package com.theblissprogrammer.boubyanbank.businesslogic.stores.posts

import com.theblissprogrammer.boubyanbank.businesslogic.common.LiveCompletionResponse
import com.theblissprogrammer.boubyanbank.businesslogic.logging.LogHelper
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.Post
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.PostModels

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class PostsWorker(val store: PostsStore,
                  val cacheStore: PostsCacheStore): PostsWorkerType {

    override suspend fun fetch(request: PostModels.Request, completion: LiveCompletionResponse<Array<Post>>) {
        val cache = cacheStore.fetch(request = request).await()

        // Immediately return local response
        completion(cache)

        val posts = store.fetch(request).await()

        if (!posts.isSuccess || posts.value == null) {
            return LogHelper.e(messages = *arrayOf("Error occurred while retrieving posts : ${posts.error ?: ""}"))
        }

        val savedElement = this.cacheStore.createOrUpdate(*posts.value.toTypedArray()).await()

        if (!savedElement.isSuccess) {
            return LogHelper.e(
                messages = *arrayOf(
                    "Could not save updated posts locally" +
                            " from remote storage: ${savedElement.error?.localizedMessage ?: ""}"
                )
            )
        }
    }

    override suspend fun fetch(request: PostModels.PostRequest, completion: LiveCompletionResponse<Post>) {
        val cache = cacheStore.fetch(request = request).await()

        // Immediately return local response
        completion(cache)
    }
}