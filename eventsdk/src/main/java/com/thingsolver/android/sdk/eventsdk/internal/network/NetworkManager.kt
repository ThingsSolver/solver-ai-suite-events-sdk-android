package com.thingsolver.android.sdk.eventsdk.internal.network

import com.thingsolver.android.sdk.eventsdk.internal.model.CollectionDataExt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class NetworkManager(baseUrl: String) {

//    val client = OkHttpClient.Builder()
//        .addInterceptor(HttpLoggingInterceptor().apply {
//            setLevel(HttpLoggingInterceptor.Level.BODY)
//        })
//        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
//        .client(client)
        .build()

    private val service: EventService = retrofit.create(EventService::class.java)

    internal suspend fun collect() {

    }

    internal suspend fun bulk(apiKey: String, tenantId: String, events: List<CollectionDataExt>) {
        service.bulk(apiKey, tenantId, BulkBody(events))
    }

    internal suspend fun custom() {

    }

}