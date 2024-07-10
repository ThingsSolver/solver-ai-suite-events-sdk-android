package com.thingsolver.android.sdk.eventsdk.internal.model

import com.google.gson.annotations.SerializedName

internal data class Argument(
    @SerializedName("arg_name") val name: String,
    @SerializedName("arg_value") val value: String
) {

    companion object {
        private const val SEPARATOR = "#=#"

        fun fromString(s: String): Argument {
            val (name, value) = s.split(SEPARATOR)
            return Argument(name, value)
        }
    }

    override fun toString(): String {
        return "$name$SEPARATOR$value"
    }

}