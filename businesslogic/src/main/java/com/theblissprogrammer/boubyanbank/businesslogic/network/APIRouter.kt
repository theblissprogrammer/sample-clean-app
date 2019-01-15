package com.theblissprogrammer.boubyanbank.businesslogic.network

import android.net.Uri
import com.theblissprogrammer.boubyanbank.businesslogic.enums.NetworkMethod
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.ConstantsType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.UserModels

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
sealed class APIRouter: APIRoutable() {

    class ReadUsers(val request: UserModels.Request? = null): APIRouter() {
        override val method = NetworkMethod.GET
        override val path = "users"
        override val queryParameterList = {
            val param = arrayListOf<Pair<String, Any>>()

            if (request?.id != null) {
                param.add(Pair("id", request.id))
            }

            param
        }()
    }

    class ReadPosts(val id: Int): APIRouter() {
        override val method = NetworkMethod.GET
        override val path = "posts"
        override val queryParameterList = {
            val param = arrayListOf<Pair<String, Any>>()
            param.add(Pair("userId", id))

            param
        }()
    }

    override fun getURI(constants: ConstantsType): Uri.Builder = Uri.parse(constants.baseURL)
        .buildUpon()
        .appendEncodedPath(constants.baseRESTPath)
        .appendEncodedPath(path)
}