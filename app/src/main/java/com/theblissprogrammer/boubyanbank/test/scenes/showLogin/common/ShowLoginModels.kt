package com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

sealed class ShowLoginModels {
    class ViewModel : ShowLoginModels() {
        inner class Error(val title: String, val message: String?)
    }
}