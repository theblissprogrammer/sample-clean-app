package com.theblissprogrammer.boubyanbank.businesslogic.network

import android.content.Context
import com.theblissprogrammer.boubyanbank.businesslogic.common.NetworkResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.NetworkResult.Companion.failure
import com.theblissprogrammer.boubyanbank.businesslogic.common.NoInternetException
import com.theblissprogrammer.boubyanbank.businesslogic.common.isNetworkAvailable
import com.theblissprogrammer.boubyanbank.businesslogic.enums.SecurityProperty
import com.theblissprogrammer.boubyanbank.businesslogic.errors.NetworkError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.scrubbed
import com.theblissprogrammer.boubyanbank.businesslogic.logging.LogHelper
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.ConstantsType
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityWorkerType
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

internal class APISession(private val context: Context?,
                          private val constants: ConstantsType,
                          private val securityWorker: SecurityWorkerType): APISessionType {

    private val sessionManager : OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).build()


    /// Determine if user is authenticated with server
    override val isAuthorized: Boolean
        get() {
            // Assume user is logged in if they have a JWT token.
            // However, future requests can return a 401-unauthorized,
            // which should log user out and redirect to login screen.
            return !securityWorker.get(SecurityProperty.JWT).isNullOrBlank()
        }

    /// Remove stored tokens and credentials so requests will be unauthorized
    override fun unauthorize() {
        securityWorker.clear()
    }

    /// Creates a network request to retrieve the contents of a URL based on the specified router.
    ///
    /// - Parameters:
    ///   - router: The router request.
    ///   - completion: A handler to be called once the request has finished
    override fun request(router: APIRoutable): NetworkResult<ServerResponse> {
        // Validate connectivity
        if (!isNetworkAvailable(context = context)) {
            return failure(NetworkError(internalError = NoInternetException()))
        }

        val urlRequestBuilder: Request.Builder

        // Construct request
        try {
            urlRequestBuilder = router.asURLRequest(constants)

            // Add any extra headers here
        } catch (exception: Exception) {
            return failure(NetworkError(internalError = exception))
        }

        val urlRequest = urlRequestBuilder.build()

        // Log request/response debugging
        LogHelper.i(messages = *arrayOf("Request: $urlRequest"))

        val response = sessionManager.request(urlRequest)

        // Handle any pre-processing if applicable
        if (response.isSuccess && response.value != null) {
            LogHelper.d(messages = *arrayOf("Response: {\n\turl: ${urlRequest?.url()?.url()?.toString()}," +
                    "\n\tstatus: ${response.value.statusCode}, \n\theaders: ${response.value.headers.scrubbed}\n}"))
        } else if (response.error != null ){
            LogHelper.d(messages = *arrayOf("Network request error: ${response.error.description}"))
        }

        return response
    }
}