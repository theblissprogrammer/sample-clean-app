package com.theblissprogrammer.boubyanbank.businesslogic.account.models


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
sealed class AccountModels {
    class ServerResponse(
        val userID: String
    ): AccountModels()

    class Response(
        val userID: String
    ): AccountModels()
}