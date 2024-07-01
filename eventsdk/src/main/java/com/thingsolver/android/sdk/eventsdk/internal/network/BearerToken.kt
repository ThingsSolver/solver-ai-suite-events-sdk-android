package com.thingsolver.android.sdk.eventsdk.internal.network

import com.google.gson.annotations.SerializedName

internal data class BearerToken (

    @SerializedName("access_token")
    val access_token: String,

    @SerializedName("expires_in")
    val expires_in: Int,

    @SerializedName("refresh_expires_in")
    val refresh_expires_in: Int,

    @SerializedName("refresh_token")
    val refresh_token: String,

    @SerializedName("token_type")
    val token_type: String,

    @SerializedName("not_before_policy")
    val not_before_policy: Int,

    @SerializedName("session_state")
    val session_state: String,

    @SerializedName("id_token")
    val id_token: String,

    @SerializedName("scope")
    val scope: String

)