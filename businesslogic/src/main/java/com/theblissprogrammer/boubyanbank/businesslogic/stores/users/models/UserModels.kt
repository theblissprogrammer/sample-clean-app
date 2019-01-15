package com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
sealed class UserModels {
    class Request(
        val id: Int? = null): UserModels()

    class SearchRequest(
        val query: String = ""): UserModels()
}