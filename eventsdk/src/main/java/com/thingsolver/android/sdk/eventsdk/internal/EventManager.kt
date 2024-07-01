package com.thingsolver.android.sdk.eventsdk.internal

import android.content.Context
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.util.Log
import com.thingsolver.android.sdk.eventsdk.Config
import com.thingsolver.android.sdk.eventsdk.internal.storage.FileManager
import com.thingsolver.android.sdk.eventsdk.internal.model.Argument
import com.thingsolver.android.sdk.eventsdk.internal.model.CollectionDataExt
import com.thingsolver.android.sdk.eventsdk.internal.network.NetworkManager
import com.thingsolver.android.sdk.eventsdk.model.CollectionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

internal object EventManager {

    private var isInitialized = false

    private val dataCollected = mutableListOf<CollectionDataExt>()

    private var lastSentTimestamp = System.currentTimeMillis()

    private var lastSessionIdCreatedTimestamp = 0L

    private lateinit var fileManager: FileManager

    private lateinit var networkManager: NetworkManager

    internal lateinit var config: Config

    private lateinit var sessionId: String

    private lateinit var appContext: WeakReference<Context>

    private val dataOperationMutex = Mutex()

    internal var optOut = false

    internal fun start(context: Context, config: Config) {
        if (!isInitialized) {
            isInitialized = true

            this.config = config
            generateSessionId()
            appContext = WeakReference(context.applicationContext)
            fileManager = FileManager(context.applicationContext)
            networkManager = NetworkManager(config.baseUrl)


            GlobalScope.launch(Dispatchers.IO) {
                dataCollected.addAll(fileManager.getAllData())

                if (dataCollected.size >= config.numberOfMaxEventsCollectedBeforeSending) {
                    sendData()
                }

                startPeriodicalDataSending()
            }

            GlobalScope.launch(Dispatchers.IO) {
                manageSessionId()
            }
        }
    }

    internal fun collect(collectionData: CollectionData) {
        if (!isInitialized) throw IllegalStateException("EventManager not initialized")

        if (!optOut) {
            Log.d("EventManager", "Collecting data...")

            GlobalScope.launch(Dispatchers.IO) {
                dataOperationMutex.withLock {
                    val dataToAdd = CollectionDataExt(
                        collectionData.deviceToken,
                        collectionData.customerId,
                        collectionData.loginStatus,
                        collectionData.pageType,
                        collectionData.pageName,
                        collectionData.event,
                        collectionData.eventValue,
                        collectionData.eventArguments?.map { entry ->
                            Argument(entry.key, entry.value)
                        },
                        collectionData.language,
                        collectionData.lat,
                        collectionData.lon,
                        createEventTimestamp(),
                        sessionId,
                        getAppName(appContext.get()),
                        getAppVersion(appContext.get()),
                        UUID.randomUUID().toString(),
                        getBatteryLevel(appContext.get())
                    )

                    dataCollected.add(dataToAdd)
                    fileManager.addNewData(dataToAdd)
                }

                if (dataCollected.size >= config.numberOfMaxEventsCollectedBeforeSending) {
                    sendData()
                }
            }
        }
    }

    private suspend fun startPeriodicalDataSending() {
        while (true) {
            val timestampForSending = lastSentTimestamp + config.eventSendInterval
            val timeToWait = timestampForSending - System.currentTimeMillis()

            delay(timeToWait)

            if (lastSentTimestamp <= timestampForSending - config.eventSendInterval) {
                sendData()
                lastSentTimestamp = System.currentTimeMillis()
            }
        }
    }

    internal fun generateSessionId() {
        Log.d("EventManager", "Creating new sessionId...")
        sessionId = UUID.randomUUID().toString()
        lastSessionIdCreatedTimestamp = System.currentTimeMillis()
    }

    private suspend fun manageSessionId() {
        while(true) {
            val timestampForNextSessionId = lastSessionIdCreatedTimestamp + config.eventSendInterval
            val timeToWait = timestampForNextSessionId - System.currentTimeMillis()

            delay(timeToWait)

            if (lastSessionIdCreatedTimestamp <= timestampForNextSessionId - config.sessionIdRegenerateTimeInterval) {
                generateSessionId()
                lastSessionIdCreatedTimestamp = System.currentTimeMillis()
            }
        }
    }

    private suspend fun sendData() {
        if (dataCollected.isEmpty()) return

        try {
            Log.d("EventManager", "Sending data, size ${dataCollected.size}")

            dataOperationMutex.withLock {
                networkManager.bulk(config.apiKey, config.tenantID, dataCollected)
                dataCollected.clear()
                fileManager.clearData()
            }
        } catch (t: Throwable) {
            Log.e("EventManager", "Error while sending data: $t")
        }
    }

    private fun createEventTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("CET") // Set timezone to UTC
        return dateFormat.format(Date(System.currentTimeMillis()))

    }

    private fun getAppName(context: Context?): String? {
        return context?.let { context ->
            val applicationInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                0
            )

            context.packageManager.getApplicationLabel(applicationInfo).toString()
        }
    }


    private fun getAppVersion(context: Context?): String? {
        return context?.let { context ->
            try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                Log.e("EventManager", "Error getting app version: $e")
                null
            }
        }
    }

    private fun getBatteryLevel(context: Context?): Float? {
        return context?.let { context ->
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).toFloat()
        }
    }

}