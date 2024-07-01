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