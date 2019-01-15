package com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common

import androidx.lifecycle.LiveData
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.Post

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
sealed class ListPostsModels {
    class Response(
            val posts: LiveData<Array<Post>>) : ListPostsModels()

    class ViewModel(
            val posts: LiveData<List<PostViewModel>>) : ListPostsModels()

    class PostViewModel(
            val id: Int,
            val title: String) : ListPostsModels()
}