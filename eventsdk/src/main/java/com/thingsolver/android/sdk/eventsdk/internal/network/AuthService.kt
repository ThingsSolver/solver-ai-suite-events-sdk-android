package com.thingsolver.android.sdk.eventsdk.internal.network

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface AuthService {

    @Headers("Content-Type: application/json")
    @POST("api/latest/auth/login")
    suspend fun login(
        @Body body: LoginBody
    ) : BearerToken

}