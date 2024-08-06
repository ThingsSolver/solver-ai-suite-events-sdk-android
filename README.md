# EventSDK Android

EventSDK is a lightweight and easy-to-use Android library that simplifies the process of tracking events and sending them to your analytics backend.

## Features

- Seamless Integration: Easily integrate the SDK into your Android project with minimal setup.

- Custom Event Tracking: Track any type of event with predefined or custom parameters to capture rich data.

- Batching and Offline Support: Events are batched and stored locally, ensuring data is collected even when the device is offline.

- Lightweight and Efficient: Minimal impact on your app's performance.

## Requirements

- minSdk 21

- CompileSdk 34 or greater

- Kotlin 1.9.0 or greater

## External libraries used

- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2

- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2

- com.squareup.retrofit2:retrofit:2.11.0

- com.squareup.retrofit2:converter-gson:2.11.0

If you don't already have these libraries added to your project, you can add them by adding the following lines to your app build.gradle file:
```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")
```

## Usage

### Initialization

EventSDK should be initialized first, by calling the method initialize(Context, Authentication, Config) in the Application onCreate() method, e.g.

```kotlin
class MyApplication : Application() {
   
    override fun onCreate() {
        super.onCreate()

        val authentication = AuthenticationApiKey("my-api-key")
        val myConfig = Config("dev", "https://myeventserver.com")
        EventSDK.initialize(this, authentication, myConfig, 100, 3 * 60 * 1000, 30 * 60 * 1000)
    }

}
```
Config represents the object that contains the configuration data:
```kotlin

data class Config(
    val tenantID: String,
    val baseUrl: String,
    val numberOfMaxEventsCollectedBeforeSending: Int = Defaults.DEFAULT_NUMBER_OF_MAX_EVENTS_COLLECTED_BEFORE_SENDING,
    val eventSendInterval: Long = Defaults.DEFAULT_EVENT_SEND_INTERVAL,
    val sessionIdRegenerateTimeInterval: Long = Defaults.DEFAULT_SESSION_ID_REGENERATE_TIME_INTERVAL
)
```

- `tenantID` - The tenant identifier
- `baseUrl` - The base URL for the API
- `apiKey` - The API key used for authentication
- `numberOfMaxEventsCollectedBeforeSending` - Number of max events collected before sending. Optional, default value is 30
- `eventSendInterval` - Event send interval in milliseconds. Optional, default value is 3 minutes
- `sessionIdRegenerateTimeInterval` - Session ID regenerate time interval in milliseconds. Optional, default value is 30 minutes

Authentication is a sealed class which object should hold the authentication data. There are two types of Authentication: AuthenticationApiKey and AuthenticationBearerToken:

AuthenticationApiKey:
```kotlin
    /**
     * Class that represents authentication by API key.
     * @param apiKey API key used for authentication.
     */
    class AuthenticationApiKey(val apiKey: String) : Authentication()
```
- `apiKey` - API key used for authentication.

AuthenticationBearerToken:
```kotlin
    /**
     * Class that represents authentication by Bearer token.
     * @param userName User name used for fetching bearer token.
     * @param password Password used for fetching bearer token.
     * @param authUrl URL used for authentication.
     */
    class AuthenticationBearerToken(val userName: String, val password: String, val authUrl: String) :
        Authentication()
```
- `userName` - User name used for fetching bearer token.
- `password` - Password used for fetching bearer token.
- `authUrl` - URL used for authentication.
### Event tracking

To track an event, call the method EventSDK.collect(CollectionData), e.g.


```kotlin
EventSDK.collect(
    CollectionData(
        "abc123", 
        "cust456", 
        false, 
        "home", 
        "main", 
        Event.APP_OPEN, 
        "success", 
        mapOf("arg1" to "value1", "arg2" to "value2"), 
        "en", 
        LocationUtils.lat, 
        LocationUtils.lon
    )
)
```

CollectionData represents the object that contains the tracking data:

```kotlin
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
```
- `deviceToken` - The device token associated with the user device.

- `customerId` - The customer ID associated with the user.

- `loginStatus` - Whether the user is logged in or not.

- `pageType` - The type of page the user is on.

- `pageName` - The name of the page the user is on.

- `event` - Type of the event. It can have one of the predefined values from the Event class, or a custom one.

- `eventValue` - The value of the event.

- `eventArguments` - Additional arguments for the event.

- `language` - The language the user is using.

- `lat` - The latitude of the user's location.

- `lon` - The longitude of the user's location.

Event is the class that contains predefined event constants:

```kotlin
 package com.thingsolver.android.sdk.eventsdk.model

/**
 * Predefined types of events that can be tracked.
 */
object Event {

    /**
     * Represents the event when an app is opened.
     */
    const val APP_OPEN = "appOpen"

    /**
     * Represents the event when an app is closed.
     */
    const val APP_CLOSED = "appClosed"

    /**
     * Represents the event when an app is crashed.
     */
    const val APP_CRASHED = "appCrashed"

    /**
     * Represents the event when an app is updated.
     */
    const val APP_UPDATE = "appUpdate"

    /**
     * Represents the event when an app is started.
     */
    const val APP_START = "appStart"

    /**
     * Represents the event when fingerprint is scanned.
     */
    const val FINGER_SCAN = "fingerScan"

    /**
     * Represents the event when a face is scanned.
     */
    const val FACE_SCAN = "faceScan"

    /**
     * Represents the event when a button is pressed.
     */
    const val BUTTON_TRIGGER = "buttonTrigger"

    /**
     * Represents the event when a user is logged in.
     */
    const val LOGIN = "login"

    /**
     * Represents the event when a user is logged out.
     */
    const val LOGOUT = "logout"

    /**
     * Represents the event when an engagement is served.
     */
    const val ENGAGEMENT_SERVED = "engagementServed"

    /**
     * Represents the event of a transaction.
     */
    const val TRANSACTION = "transaction"

    /**
     * Represents the event when a product is viewed.
     */
    const val PRODUCT_VIEW = "productView"

    /**
     * Represents the event when a page is viewed.
     */
    const val PAGE_VIEW = "pageView"

    /**
     * Represents the event when a material is downloaded.
     */
    const val MATERIAL_DOWNLOAD = "materialDownload"

    /**
     * Represents the event of a search query.
     */
    const val SEARCH_QUERY = "searchQuery"

}
```

###Session managment

Session ID is automatically generated when SDK is initialized. New user session is generated on new application launch, after the session lifetime has expired (Config.sessionIdRegenerateTimeInterval), or manually, by calling the method
```kotlin
EventSDK.generateSessionId()
```

###Opt-Out control
To enable/disable event data collection, call the method
```kotlin
EventSDK.setOptOut(optOut: Boolean)
```
Setting this value to `true` disables event data collection.

To check whether the event data collection is disabled or enabled, call the method
```kotlin
EventSDK.isOptOut()
```