package com.thingsolver.android.sdk.eventsdk.internal.model

import com.google.gson.annotations.SerializedName

internal class CollectionDataExt(
    @SerializedName("device_token") val device_token: String?,
    @SerializedName("customer_id") val customer_id: String?,
    @SerializedName("login_status") val login_status: Boolean,
    @SerializedName("page_type") val page_type: String?,
    @SerializedName("page_name") val page_name: String?,
    @SerializedName("event") val event: String,
    @SerializedName("event_value") val event_value: String?,
    @SerializedName("event_arguments") val event_arguments: List<Argument>?,
    @SerializedName("language") val language: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("session_id") val session_id: String,
    @SerializedName("application_name") val application_name: String?,
    @SerializedName("application_version") val application_version: String?,
    @SerializedName("client_request_id") val client_request_id: String,
    @SerializedName("battery_level") val battery_level: Float?
) {

    companion object {
        private const val VERSION = "1"
        private const val SEPARATOR_FIELD = "#!#"
        private const val SEPARATOR_COLLECTION = "!#!"

        fun fromString(value: String): CollectionDataExt {
            val parts = value.split(SEPARATOR_FIELD)

            return CollectionDataExt(
                nullOrValue(parts[1]),
                nullOrValue(parts[2]),
                parts[3] == "T",
                nullOrValue(parts[4]),
                nullOrValue(parts[5]),
                parts[6],
                nullOrValue(parts[7]),
                nullOrValue(parts[8])?.let {
                    it.split(SEPARATOR_COLLECTION).map { Argument.fromString(it) }
                },
                nullOrValue(parts[9]),
                nullOrValue(parts[10])?.toDouble(),
                nullOrValue(parts[11])?.toDouble(),
                parts[12],
                parts[13],
                nullOrValue(parts[14]),
                nullOrValue(parts[15]),
                parts[16],
                nullOrValue(parts[17])?.toFloat()
            )
        }

        private fun nullOrValue(value: String): String? {
            return value.takeIf { it != "null" }
        }
    }

    override fun toString(): String {
        return "$VERSION$SEPARATOR_FIELD" +
                "$device_token$SEPARATOR_FIELD" +
                "$customer_id$SEPARATOR_FIELD" +
                "${if (login_status) "T" else "F"}$SEPARATOR_FIELD" +
                "$page_type$SEPARATOR_FIELD" +
                "$page_name$SEPARATOR_FIELD" +
                "${event}$SEPARATOR_FIELD" +
                "$event_value$SEPARATOR_FIELD" +
                "${event_arguments?.joinToString(SEPARATOR_COLLECTION)}$SEPARATOR_FIELD" +
                "$language$SEPARATOR_FIELD" +
                "$lat$SEPARATOR_FIELD" +
                "$lon$SEPARATOR_FIELD" +
                "$timestamp$SEPARATOR_FIELD" +
                "$session_id$SEPARATOR_FIELD" +
                "$application_name$SEPARATOR_FIELD" +
                "$application_version$SEPARATOR_FIELD" +
                "$client_request_id$SEPARATOR_FIELD" +
                "$battery_level"
    }

}