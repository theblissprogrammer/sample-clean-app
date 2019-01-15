package com.theblissprogrammer.boubyanbank.businesslogic.stores.users

import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredLiveResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.DeferredResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.LiveResult
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineNetwork
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineRoom
import com.theblissprogrammer.boubyanbank.businesslogic.stores.common.insertOrUpdate
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.UserModels

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
 class UsersRoomStore(val userDao: UserDao?): UsersCacheStore {
    
    override fun fetch(request: UserModels.Request): DeferredLiveResult<Array<User>> {
        return coroutineRoom<Array<User>> {

            val items = if (request.id != null)
                userDao?.fetch(arrayOf(request.id))
            else
                userDao?.fetchAllUsers()

            if (items == null) {
                LiveResult.failure(DataError.NonExistent)
            } else {
                LiveResult.success(items)
            }
        }
    }

    override fun fetch(request: UserModels.SearchRequest): DeferredLiveResult<Array<User>> {
        return coroutineRoom<Array<User>> {

            val items = if (request.query.isNotEmpty())
                userDao?.search("%${request.query}%")
            else
                userDao?.fetchAllUsers()

            if (items == null) {
                LiveResult.failure(DataError.NonExistent)
            } else {
                LiveResult.success(items)
            }
        }
    }

    override fun createOrUpdate(user: User): DeferredLiveResult<User> {
        return coroutineRoom<User> {

            userDao?.insertOrUpdate(user)

            val item = userDao?.fetch(id = user.id)

            if (item == null) {
                LiveResult.failure(DataError.NonExistent)
            } else {
                LiveResult.success(item)
            }
        }
    }

    override fun createOrUpdate(vararg users: User): DeferredResult<Void> {
        return coroutineNetwork<Void> {
            userDao?.insert(*users)
            Result.success()
        }
    }
}