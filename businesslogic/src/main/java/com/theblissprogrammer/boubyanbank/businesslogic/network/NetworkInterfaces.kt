package com.theblissprogrammer.boubyanbank.businesslogic.network

import android.net.Uri
import com.theblissprogrammer.boubyanbank.businesslogic.common.NetworkResult
import com.theblissprogrammer.boubyanbank.businesslogic.enums.NetworkMethod
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.ConstantsType
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

interface HTTPServiceType {
    fun post(url: String, parameters: Map<String, Any?>? = null, body: String? = "",
             headers: Map<String, String>? = null): NetworkResult<ServerResponse>
    fun get(url: String, parameters: Map<String, Any?>? = null, headers: Map<String, String>? = null)
            : NetworkResult<ServerResponse>
}

interface APISessionType {
    val isAuthorized: Boolean
    fun unauthorize()
    fun request(router: APIRoutable): NetworkResult<ServerResponse>
}

abstract class APIRoutable {
    companion object {
        val JSON = MediaType.parse("application/json; charset=utf-8")
    }

    abstract val method: NetworkMethod
    abstract val path: String
    open val requestJSON: JSONObject? = null
    open val requestBody: RequestBody? = null
    open val queryParameterList: ArrayList<Pair<String, Any>>? = null

    open fun getURI(constants: ConstantsType): Uri.Builder = Uri.parse("").buildUpon()
    open fun getHeaders(constants: ConstantsType): Map<String, String>? = null

    fun asURLRequest(constants: ConstantsType) : Request.Builder {
        val uri = getURI(constants)

        queryParameterList?.forEach { uri.appendQueryParameter(it.first, it.second.toString()) }

        val requestBody =  requestBody ?: if (method == NetworkMethod.GET || method == NetworkMethod.DELETE) null else
            if (requestJSON != null) RequestBody.create(JSON, requestJSON.toString()) else
                RequestBody.create(null, "")

        val request =  Request.Builder()
            .url(uri.build().toString())
            .method(method.name, requestBody)

        getHeaders(constants)?.forEach {
            request.addHeader(it.key, it.value)
        }

        return request
    }
}