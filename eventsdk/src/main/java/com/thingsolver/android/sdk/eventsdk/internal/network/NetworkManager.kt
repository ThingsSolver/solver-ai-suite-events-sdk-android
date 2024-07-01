package com.thingsolver.android.sdk.eventsdk.internal.network

import com.thingsolver.android.sdk.eventsdk.internal.model.CollectionDataExt
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class NetworkManager(eventUrl: String, authUrl: String?) {

    val client = OkHttpClient.Builder()
//        .addInterceptor(HttpLoggingInterceptor().apply {
//            setLevel(HttpLoggingInterceptor.Level.BODY)
//        })
        .build()

    private val eventService: EventService =
        createRetrofit(eventUrl).create(EventService::class.java)

    private val authService: AuthService? =
        authUrl?.let { createRetrofit(it).create(AuthService::class.java) }

    private fun createRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    internal suspend fun bulkApiKey(apiKey: String, tenantId: String, events: List<CollectionDataExt>) {
        eventService.bulkApiKey(apiKey, tenantId, BulkBody(events))
    }

    internal suspend fun bulkBearerToken(bearerToken: String, tenantId: String, events: List<CollectionDataExt>) {
        eventService.bulkBearerToken("Bearer $bearerToken", tenantId, BulkBody(events))
    }

    internal suspend fun login(username: String, password: String): BearerToken {
        return authService!!.login(LoginBody(username, password))
    }

}