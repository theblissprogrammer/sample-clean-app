package com.theblissprogrammer.boubyanbank.businesslogic.network

import android.net.Uri
import com.theblissprogrammer.boubyanbank.businesslogic.common.NetworkResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.NetworkResult.Companion.failure
import com.theblissprogrammer.boubyanbank.businesslogic.common.NetworkResult.Companion.success
import com.theblissprogrammer.boubyanbank.businesslogic.enums.NetworkMethod
import com.theblissprogrammer.boubyanbank.businesslogic.errors.NetworkError
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

data class ServerResponse (val data: String, val headers: Map<String, String>, val statusCode: Int)

class HTTPService: HTTPServiceType {
    private val sessionManager : OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS).build()

    override fun post(url: String, parameters: Map<String, Any?>?, body: String?, headers: Map<String, String>?)
            : NetworkResult<ServerResponse> {
        val uri = Uri.parse(url)
                .buildUpon()

        try {
            parameters?.forEach { if (it.value != null) uri.appendQueryParameter(it.key, it.value.toString()) }
        } catch (error: Exception) {
            return failure(
                    NetworkError(
                            statusCode = 0,
                            internalError = error
                    )
            )
        }

        val urlRequest = Request.Builder()
                .url(uri.build().toString())
                .method(NetworkMethod.POST.name, RequestBody.create(APIRoutable.JSON, body ?: ""))

        headers?.forEach {
            urlRequest.addHeader(it.key, it.value)
        }

        return sessionManager.request(urlRequest.build())
    }

    override fun get(url: String, parameters: Map<String, Any?>?, headers: Map<String, String>?):
            NetworkResult<ServerResponse> {
        val uri = Uri.parse(url)
                .buildUpon()

        try {
            parameters?.forEach { if (it.value != null) uri.appendQueryParameter(it.key, it.value.toString()) }
        } catch (error: Exception) {
            return failure(
                    NetworkError(
                            statusCode = 0,
                            internalError = error
                    )
            )
        }

        val urlRequest = Request.Builder()
                .url(uri.build().toString())
                .method(NetworkMethod.GET.name, null)

        headers?.forEach {
            urlRequest.addHeader(it.key, it.value)
        }

        return sessionManager.request(urlRequest.build())
    }
}

/// Creates a network request to retrieve the contents of a URL based on the specified urlRequest.
///
/// - Parameters:
///   - urlRequest: The URL request.
///   - completion: A handler to be called once the request has finished.
fun OkHttpClient.request(urlRequest: Request): NetworkResult<ServerResponse> {
    val response = this.newCall(urlRequest).execute()

    // Retrieve the data
    val data = response.body()?.string()
    // Convert header values to string dictionary
    val responseHeaders = response.headers().toMultimap().mapValues { it.value.first() }
    val statusCode = response.code()

    if (response.isSuccessful) {
        return success(
                ServerResponse(
                        data = data ?: "",
                        headers = responseHeaders,
                        statusCode = statusCode
                )
        )
    } else {
        val error = NetworkError(
                urlRequest = null,
                statusCode = statusCode,
                headerValues = responseHeaders,
                serverData = data ?: "")
        return failure(error)
    }
}