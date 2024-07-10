package com.thingsolver.android.sdk.eventsdk.internal.network

import com.thingsolver.android.sdk.eventsdk.internal.model.CollectionDataExt

internal data class BulkBody(val events: List<CollectionDataExt>)