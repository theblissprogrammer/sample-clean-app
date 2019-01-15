package com.theblissprogrammer.boubyanbank.test.scenes.showPost.common

import androidx.lifecycle.LiveData
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.Post

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
sealed class ShowPostModels {
    class Response(
        val post: LiveData<Post>) : ShowPostModels()

    class ViewModel(
        val post: LiveData<PostViewModel>) : ShowPostModels()

    class PostViewModel(
        val id: Int,
        val title: String,
        val description: String?) : ShowPostModels()
}