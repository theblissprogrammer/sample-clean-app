package com.theblissprogrammer.boubyanbank.businesslogic.stores.users

import com.theblissprogrammer.boubyanbank.businesslogic.common.LiveCompletionResponse
import com.theblissprogrammer.boubyanbank.businesslogic.logging.LogHelper
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.UserModels

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class UsersWorker(val store: UsersStore,
                  val cacheStore: UsersCacheStore): UsersWorkerType {

    override suspend fun fetch(request: UserModels.Request, completion: LiveCompletionResponse<Array<User>>) {
        val cache = cacheStore.fetch(request = request).await()

        // Immediately return local response
        completion(cache)

        val users = store.fetch(request).await()

        if (!users.isSuccess || users.value == null) {
            return LogHelper.e(messages = *arrayOf("Error occurred while retrieving users : ${users.error ?: ""}"))
        }

        val savedElement = this.cacheStore.createOrUpdate(*users.value.toTypedArray()).await()

        if (!savedElement.isSuccess) {
            return LogHelper.e(
                messages = *arrayOf(
                    "Could not save updated users locally" +
                            " from remote storage: ${savedElement.error?.localizedMessage ?: ""}"
                )
            )
        }
    }

    override suspend fun fetch(request: UserModels.SearchRequest, completion: LiveCompletionResponse<Array<User>>) {
        val cache = cacheStore.fetch(request = request).await()

        // Immediately return local response
        completion(cache)
    }
}