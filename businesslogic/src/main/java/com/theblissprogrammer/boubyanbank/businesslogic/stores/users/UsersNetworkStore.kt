package com.theblissprogrammer.boubyanbank.businesslogic.stores.users

import com.google.gson.Gson
import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result
import com.theblissprogrammer.boubyanbank.businesslogic.common.initDataError
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineNetwork
import com.theblissprogrammer.boubyanbank.businesslogic.logging.LogHelper
import com.theblissprogrammer.boubyanbank.businesslogic.network.APIRouter
import com.theblissprogrammer.boubyanbank.businesslogic.network.APISessionType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.UserModels
import com.google.gson.reflect.TypeToken

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class UsersNetworkStore(val apiSession: APISessionType): UsersStore {
    override fun fetch(request: UserModels.Request): DeferredResult<List<User>> {
        return coroutineNetwork <List<User>> {
            val response = apiSession.request(router = APIRouter.ReadUsers(request))

            // Handle errors
            val value = response.value
            if (value == null || !response.isSuccess) {
                val error = response.error

                return@coroutineNetwork if (error != null) {
                    val exception = initDataError(response.error)
                    LogHelper.e(messages = *arrayOf("An error occurred while fetching users: " +
                            "${error.description}."))
                    Result.failure(exception)
                } else {
                    Result.failure(DataError.UnknownReason(null))
                }
            }

            try {
                // Parse response data
                val collectionType = object : TypeToken<List<User>>(){}.type

                val payload = Gson().fromJson(value.data, collectionType) as List<User>
                Result.success(payload)

            } catch(e: Exception) {
                LogHelper.e(messages = *arrayOf("An error occurred while parsing users: " +
                        "${e.localizedMessage ?: ""}."))
                Result.failure(DataError.ParseFailure(e))
            }
        }
    }
}