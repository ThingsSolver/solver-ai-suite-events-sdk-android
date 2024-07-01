package com.thingsolver.android.sdk.eventsdk

import android.content.Context
import com.thingsolver.android.sdk.eventsdk.internal.EventManager
import com.thingsolver.android.sdk.eventsdk.model.Authentication
import com.thingsolver.android.sdk.eventsdk.model.CollectionData

object EventSDK {

    /**
     * Initializes the SDK.
     * @param context Application context.
     * @param config Configuration object.
     */
    fun initialize(context: Context, authentication: Authentication, config: Config) {
        EventManager.start(context, authentication, config)
    }

    /**
     * Collects the tracking data.
     * @param collectionData Object containing the tracking data.
     */
    fun collect(collectionData: CollectionData) {
        EventManager.collect(collectionData)
    }

    /**
     * optOut getter.
     * @return optOut value. If true, the SDK will not collect any data.
     */
    fun isOptOut(): Boolean {
        return EventManager.optOut
    }

    /**
     * optOut setter.
     * @param optOut optOut value. If true, the SDK will not collect any data.
     */
    fun setOptOut(optOut: Boolean) {
        EventManager.optOut = optOut
    }

    /**
     * Generates a new session id.
     */
    fun generateSessionId() {
        EventManager.generateSessionId()
    }

}