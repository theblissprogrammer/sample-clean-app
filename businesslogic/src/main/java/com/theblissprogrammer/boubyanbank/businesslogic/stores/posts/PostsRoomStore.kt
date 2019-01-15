package com.theblissprogrammer.boubyanbank.businesslogic.stores.posts

import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredLiveResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.LiveResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineNetwork
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineRoom
import com.theblissprogrammer.boubyanbank.businesslogic.stores.common.insertOrUpdate
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.Post
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.PostModels

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
 class PostsRoomStore(val postDao: PostDao?): PostsCacheStore {
    
    override fun fetch(request: PostModels.Request): DeferredLiveResult<Array<Post>> {
        return coroutineRoom<Array<Post>> {

            val items = postDao?.fetchByUser(request.userId)

                LiveResult.success(items)
        }
    }

    override fun fetch(request: PostModels.PostRequest): DeferredLiveResult<Post> {
        return coroutineRoom<Post> {

            val item = postDao?.fetchByUser(id = request.id, userId = request.userId)

            LiveResult.success(item)
        }
    }

    override fun createOrUpdate(post: Post): DeferredLiveResult<Post> {
        return coroutineRoom<Post> {

            postDao?.insertOrUpdate(post)

            val item = postDao?.fetch(id = post.id)

            if (item == null) {
                LiveResult.failure(DataError.NonExistent)
            } else {
                LiveResult.success(item)
            }
        }
    }

    override fun createOrUpdate(vararg posts: Post): DeferredResult<Void> {
        return coroutineNetwork<Void> {
            postDao?.insert(*posts)
            Result.success()
        }
    }
}