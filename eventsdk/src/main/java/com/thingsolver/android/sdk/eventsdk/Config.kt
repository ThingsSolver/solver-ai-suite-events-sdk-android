package com.thingsolver.android.sdk.eventsdk

/**
 * @param tenantID - The tenant identifier
 * @param baseUrl - The base URL for the API
 * @param numberOfMaxEventsCollectedBeforeSending - Number of max events collected before sending. Optional, default value is 30
 * @param eventSendInterval - Event send interval in milliseconds. Optional, default value is 3 minutes.
 * @param sessionIdRegenerateTimeInterval - Session ID regenerate time interval in milliseconds. Optional, default value is 30 minutes
 */
data class Config(
    val tenantID: String,
    val baseUrl: String,
    val numberOfMaxEventsCollectedBeforeSending: Int = Defaults.DEFAULT_NUMBER_OF_MAX_EVENTS_COLLECTED_BEFORE_SENDING,
    val eventSendInterval: Long = Defaults.DEFAULT_EVENT_SEND_INTERVAL,
    val sessionIdRegenerateTimeInterval: Long = Defaults.DEFAULT_SESSION_ID_REGENERATE_TIME_INTERVAL
)