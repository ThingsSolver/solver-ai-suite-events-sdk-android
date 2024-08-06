package com.thingsolver.android.sdk.eventsdk.internal.network

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface EventService {

    @Headers("Content-Type: application/json")
    @POST("/api/latest/events/api/collect")
    suspend fun collect()

    @Headers("Content-Type: application/json")
    @POST("/api/latest/events/api/bulk")
    suspend fun bulkApiKey(
        @Header("x-api-key") apiKey: String?,
        @Header("tenant_id") tenantId: String,
        @Body body: BulkBody
    )

    @Headers("Content-Type: application/json")
    @POST("/api/latest/events/api/bulk")
    suspend fun bulkBearerToken(
        @Header("Authorization") bearerToken: String?,
        @Header("tenant_id") tenantId: String,
        @Body body: BulkBody
    )

    @Headers("Content-Type: application/json")
    @POST("/api/latest/events/api/custom")
    suspend fun custom()

}