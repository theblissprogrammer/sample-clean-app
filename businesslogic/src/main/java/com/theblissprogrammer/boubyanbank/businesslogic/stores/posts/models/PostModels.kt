package com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
sealed class PostModels {
    class Request(
        val userId: Int): PostModels()

    class PostRequest(
        val userId: Int,
        val id: Int): PostModels()
}