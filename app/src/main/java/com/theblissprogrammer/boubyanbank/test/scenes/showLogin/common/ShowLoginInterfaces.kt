package com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common

import com.theblissprogrammer.boubyanbank.businesslogic.account.models.LoginModels
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.test.common.routers.AppRoutable


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface ShowLoginDisplayable {
    fun displayAuthenticated(response: ShowLoginModels.ViewModel)
    fun displayAuthenticated(error: ShowLoginModels.ViewModel.Error)
}

interface ShowLoginBusinessLogic {
    fun performLogin(request: LoginModels.Request)
}

interface ShowLoginPresentable {
    fun presentAuthenticated()
    fun presentAuthenticated(error: DataError)
}

interface ShowLoginRoutable: AppRoutable {
    fun successfulLogin()
}

interface AuthenticationDelegate {
    fun didAuthenticate()
}