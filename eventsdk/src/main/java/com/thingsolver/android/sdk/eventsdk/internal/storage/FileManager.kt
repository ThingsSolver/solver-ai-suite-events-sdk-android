package com.thingsolver.android.sdk.eventsdk.internal.storage

import android.content.Context
import com.thingsolver.android.sdk.eventsdk.internal.model.CollectionDataExt
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class FileManager(applicationContext: Context) {

    private val prefs = applicationContext.getSharedPreferences("eventsdk", Context.MODE_PRIVATE)

    private val prefsMutex = Mutex()

    internal suspend fun getAllData(): List<CollectionDataExt> {
        return prefsMutex.withLock {
            prefs.all.mapNotNull {
                CollectionDataExt.fromString(it.value as String)
            }
        }
    }

    internal suspend fun addNewData(data: CollectionDataExt) {
        prefsMutex.withLock {
            prefs.edit()
                .putString(data.client_request_id, data.toString())
                .commit()
        }
    }

    internal suspend fun clearData() {
        prefsMutex.withLock {
            prefs.edit()
                .clear()
                .commit()
        }
    }

}