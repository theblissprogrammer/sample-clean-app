package com.theblissprogrammer.boubyanbank.businesslogic.account

import com.theblissprogrammer.boubyanbank.businesslogic.account.models.AccountModels
import com.theblissprogrammer.boubyanbank.businesslogic.account.models.LoginModels
import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredResult
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineNetwork
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result.Companion.failure
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result.Companion.success
import com.theblissprogrammer.boubyanbank.businesslogic.common.initDataError
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.logging.LogHelper
import com.theblissprogrammer.boubyanbank.businesslogic.network.APIRouter
import com.theblissprogrammer.boubyanbank.businesslogic.network.APISessionType
import com.theblissprogrammer.boubyanbank.businesslogic.network.ServerResponse

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class AuthenticationNetworkService(val apiSession: APISessionType) : AuthenticationService {

    /// Determines if the user is signed in with a token for authorized requests.
    override val isAuthorized
            get() = apiSession.isAuthorized

    /// Pings remote server to verify authorization still valid.
    override fun pingAuthorization(): DeferredResult<Void> {
        return coroutineNetwork <Void> {
            val response = apiSession.request(APIRouter.ReadUsers())
            val error = initDataError(response.error)

            /* Only explicit unauthorized error will be checked */
            if (response.isSuccess && error is DataError.Other) {
                success()
            } else {
                failure(error)
            }
        }
    }

    override fun login(request: LoginModels.Request): DeferredResult<AccountModels.ServerResponse> {

        return coroutineNetwork <AccountModels.ServerResponse> {
            val response = apiSession.request(router = APIRouter.ReadUsers())

            // Handle errors
            val value = response.value
            if (value == null || !response.isSuccess) {
                val error = response.error

                return@coroutineNetwork if (error != null) {
                    val exception = initDataError(response.error)
                    LogHelper.e(messages = *arrayOf("An error occurred while login: " +
                            "${error.description}."))
                    failure(exception)
                } else {
                    failure(DataError.UnknownReason(null))
                }
            }

            try {
                // Parse response data

                // Since no authentication actually exists for this project simply return a generic object
                // if the user can read from the api.
                val serverResponse = AccountModels.ServerResponse(
                    userID = "0"
                )

                success(serverResponse)
            } catch(e: Exception) {
                LogHelper.e(messages = *arrayOf("An error occurred while parsing login: " +
                        "${e.localizedMessage ?: ""}."))
                failure(DataError.ParseFailure(e))
            }
        }
    }
}