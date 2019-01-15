package com.theblissprogrammer.boubyanbank.businesslogic.stores.users

import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredLiveResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.LiveCompletionResponse
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.UserModels

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface UsersStore {
    fun fetch(request: UserModels.Request): DeferredResult<List<User>>
}

interface UsersCacheStore{
    fun fetch(request: UserModels.Request): DeferredLiveResult<Array<User>>
    fun fetch(request: UserModels.SearchRequest): DeferredLiveResult<Array<User>>
    fun createOrUpdate(user: User): DeferredLiveResult<User>
    fun createOrUpdate(vararg users: User): DeferredResult<Void>
}

interface UsersWorkerType {
    suspend fun fetch(request: UserModels.Request, completion: LiveCompletionResponse<Array<User>>)
    suspend fun fetch(request: UserModels.SearchRequest, completion: LiveCompletionResponse<Array<User>>)
}