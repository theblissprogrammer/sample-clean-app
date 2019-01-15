package com.theblissprogrammer.boubyanbank.test.scenes.showLogin

import com.theblissprogrammer.boubyanbank.businesslogic.account.AuthenticationWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.account.models.LoginModels
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineOnUi
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common.ShowLoginBusinessLogic
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common.ShowLoginPresentable

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ShowLoginInteractor(val presenter: ShowLoginPresentable,
                          val authenticationWorker: AuthenticationWorkerType) : ShowLoginBusinessLogic {

    override fun performLogin(request: LoginModels.Request) {
        coroutineOnUi {
            authenticationWorker.login(request) {
                // Handle error if applicable
                if (it.value == null || !it.isSuccess) {
                    this.presenter.presentAuthenticated(error = it.error ?: DataError.UnknownReason(null))
                    return@login
                } else {
                    this.presenter.presentAuthenticated()
                }
            }
        }
    }
}