package com.theblissprogrammer.boubyanbank.businesslogic.account.models


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

sealed class LoginModels {
    class Request(var username: String,
                  var password: String) : LoginModels()
}