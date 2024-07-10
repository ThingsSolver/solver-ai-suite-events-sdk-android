package com.thingsolver.android.sdk.eventsdk.model

/**
 * A class that holds data for a single event.
 * @param deviceToken The device token associated with the user device.
 * @param customerId The customer ID associated with the user.
 * @param loginStatus Whether the user is logged in or not.
 * @param pageType The type of page the user is on.
 * @param pageName The name of the page the user is on.
 * @param event Type of the event. Could be predefined (@see com.thingsolver.android.sdk.eventsdk.model.Event) or custom.
 * @param eventValue The value of the event.
 * @param eventArguments Additional arguments for the event.
 * @param language The language the user is using.
 * @param lat The latitude of the user's location.
 * @param lon The longitude of the user's location.
 */
open class CollectionData(
    val deviceToken: String?,
    val customerId: String?,
    val loginStatus: Boolean,
    val pageType: String?,
    val pageName: String?,
    val event: String,
    val eventValue: String?,
    val eventArguments: Map<String, String>?,
    val language: String?,
    val lat: Double?,
    val lon: Double?
)