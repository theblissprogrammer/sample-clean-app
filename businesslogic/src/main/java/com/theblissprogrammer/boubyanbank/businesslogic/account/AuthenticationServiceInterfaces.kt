package com.theblissprogrammer.boubyanbank.businesslogic.account

import com.theblissprogrammer.boubyanbank.businesslogic.account.models.AccountModels
import com.theblissprogrammer.boubyanbank.businesslogic.account.models.LoginModels
import com.theblissprogrammer.boubyanbank.businesslogic.common.CompletionResponse
import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredResult

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

interface AuthenticationService {
    val isAuthorized: Boolean
    fun pingAuthorization(): DeferredResult<Void>
    fun login(request: LoginModels.Request): DeferredResult<AccountModels.ServerResponse>
}

interface AuthenticationWorkerType {
    val isAuthorized: Boolean
    suspend fun pingAuthorization(completion: CompletionResponse<Void>)
    suspend fun login(request: LoginModels.Request,
                      completion: CompletionResponse<AccountModels.Response>)
    fun logout()
}