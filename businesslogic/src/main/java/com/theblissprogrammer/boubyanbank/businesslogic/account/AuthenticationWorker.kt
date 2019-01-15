package com.theblissprogrammer.boubyanbank.businesslogic.account

import android.content.Context
import com.theblissprogrammer.boubyanbank.businesslogic.account.models.AccountModels
import com.theblissprogrammer.boubyanbank.businesslogic.account.models.LoginModels
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result.Companion.failure
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result.Companion.success
import com.theblissprogrammer.boubyanbank.businesslogic.common.CompletionResponse
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result
import com.theblissprogrammer.boubyanbank.businesslogic.enums.DefaultsKeys.Companion.userID
import com.theblissprogrammer.boubyanbank.businesslogic.enums.SecurityProperty
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.ACTION_AUTHENTICATION_DID_LOGIN
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.ACTION_AUTHENTICATION_DID_LOGOUT
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.USER_ID
import com.theblissprogrammer.boubyanbank.businesslogic.logging.LogHelper
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.PreferencesWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityWorkerType


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class AuthenticationWorker(val service: AuthenticationService,
                           val preferencesWorker: PreferencesWorkerType,
                           val securityWorker: SecurityWorkerType,
                           val context: Context?) : AuthenticationWorkerType {

    /// Determine if user is authenticated on server and local level
    override val isAuthorized
            get() = service.isAuthorized && !preferencesWorker.get(userID).isNullOrEmpty()

    /// Pings remote service to verify authorization still valid.
    override suspend fun pingAuthorization(completion: CompletionResponse<Void>) {
        completion(service.pingAuthorization().await())
    }

    override suspend fun login(request: LoginModels.Request,
                               completion: CompletionResponse<AccountModels.Response>) {
        // Validate input
        if (request.username.isBlank() ||
                request.password.isBlank()) {
           return completion(failure(DataError.Incomplete))
        }

        val response = service.login(request).await()


        if (!response.isSuccess || response.value == null) {
            LogHelper.e(messages = *arrayOf("Could not extract value from login response."))
            completion(failure(DataError.Unauthorized))
            return
        }

        authenticated(request = request, response = response.value) {

            // Notify application authentication occurred and ready
            if (context != null){
                val intent = Intent()
                intent.action = ACTION_AUTHENTICATION_DID_LOGIN
                intent.putExtra(USER_ID, it.value?.userID)

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
            }

            LogHelper.i(messages = *arrayOf("Login complete for user #${it.value?.userID}."))

            completion(success(it.value))
        }
    }

    override fun logout() {
        // Save logged out user ID before cleared out
        val userID = preferencesWorker.get(userID)

        LogHelper.d(messages = *arrayOf("Log out for user #${userID ?: ""} begins..."))

        securityWorker.clear()
        preferencesWorker.clear()

        LogHelper.i(messages = *arrayOf("Log out complete for user #${userID ?: 0}."))

        // Notify application authentication occurred and ready
        val context = context ?: return

        val intent = Intent()
        intent.action = ACTION_AUTHENTICATION_DID_LOGOUT
        intent.putExtra(USER_ID, userID ?: "")

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    /// Used for handling successfully completed signup or login
    private suspend fun authenticated(request: LoginModels.Request, response: AccountModels.ServerResponse, completion: suspend (Result<AccountModels.Response>) -> Unit){

        securityWorker.clear()

        // Store user info for later use on app start and db initialization
        // NOTE: In a production app token and id would be retrieved from the api. Here we just hardcode it.
        securityWorker.set(token = "Dummy Token", email = request.username, password = request.password)
        securityWorker.set(key = SecurityProperty.TOKEN, value = "Random Token")
        preferencesWorker.set(response.userID, key = userID)

        completion(success(AccountModels.Response(response.userID)))
    }
}