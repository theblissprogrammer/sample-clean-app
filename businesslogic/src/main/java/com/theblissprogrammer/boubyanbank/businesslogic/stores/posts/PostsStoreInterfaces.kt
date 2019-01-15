package com.theblissprogrammer.boubyanbank.businesslogic.stores.posts

import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredLiveResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.LiveCompletionResponse
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.Post
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.PostModels

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface PostsStore {
    fun fetch(request: PostModels.Request): DeferredResult<List<Post>>
}

interface PostsCacheStore{
    fun fetch(request: PostModels.Request): DeferredLiveResult<Array<Post>>
    fun fetch(request: PostModels.PostRequest): DeferredLiveResult<Post>
    fun createOrUpdate(post: Post): DeferredLiveResult<Post>
    fun createOrUpdate(vararg posts: Post): DeferredResult<Void>
}

interface PostsWorkerType {
    suspend fun fetch(request: PostModels.Request, completion: LiveCompletionResponse<Array<Post>>)
    suspend fun fetch(request: PostModels.PostRequest, completion: LiveCompletionResponse<Post>)
}