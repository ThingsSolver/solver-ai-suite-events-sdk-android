package com.thingsolver.android.sdk.eventsdk.model

/**
 * Sealed class that represents authentication.
 */
sealed class Authentication {

    /**
     * Class that represents authentication by API key.
     * @param apiKey API key used for authentication.
     */
    class AuthenticationApiKey(val apiKey: String) : Authentication()

    /**
     * Class that represents authentication by Bearer token.
     * @param userName User name used for fetching bearer token.
     * @param password Password used for fetching bearer token.
     * @param authUrl URL used for authentication.
     */
    class AuthenticationBearerToken(val userName: String, val password: String, val authUrl: String) :
        Authentication()

}