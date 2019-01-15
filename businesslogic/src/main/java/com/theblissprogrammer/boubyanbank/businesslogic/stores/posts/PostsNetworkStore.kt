package com.theblissprogrammer.boubyanbank.businesslogic.stores.posts

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result
import com.theblissprogrammer.boubyanbank.businesslogic.common.initDataError
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineNetwork
import com.theblissprogrammer.boubyanbank.businesslogic.logging.LogHelper
import com.theblissprogrammer.boubyanbank.businesslogic.network.APIRouter
import com.theblissprogrammer.boubyanbank.businesslogic.network.APISessionType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.Post
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.PostModels

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class PostsNetworkStore(val apiSession: APISessionType): PostsStore {
    override fun fetch(request: PostModels.Request): DeferredResult<List<Post>> {
        return coroutineNetwork <List<Post>> {
            val response = apiSession.request(router = APIRouter.ReadPosts(request.userId))

            // Handle errors
            val value = response.value
            if (value == null || !response.isSuccess) {
                val error = response.error

                return@coroutineNetwork if (error != null) {
                    val exception = initDataError(response.error)
                    LogHelper.e(messages = *arrayOf("An error occurred while fetching posts: " +
                            "${error.description}."))
                    Result.failure(exception)
                } else {
                    Result.failure(DataError.UnknownReason(null))
                }
            }

            try {
                // Parse response data
                val collectionType = object : TypeToken<List<Post>>(){}.type

                val payload = Gson().fromJson(value.data, collectionType) as List<Post>
                Result.success(payload)

            } catch(e: Exception) {
                LogHelper.e(messages = *arrayOf("An error occurred while parsing posts: " +
                        "${e.localizedMessage ?: ""}."))
                Result.failure(DataError.ParseFailure(e))
            }
        }
    }
}