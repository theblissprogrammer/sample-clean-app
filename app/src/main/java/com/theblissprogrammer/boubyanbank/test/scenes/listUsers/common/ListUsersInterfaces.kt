package com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common

import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.UserModels
import com.theblissprogrammer.boubyanbank.test.common.routers.AppDisplayable
import com.theblissprogrammer.boubyanbank.test.common.routers.AppRoutable

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface ListUsersDisplayable: AppDisplayable {
    fun displayFetchedUsers(viewModel: ListUsersModels.ViewModel)
}

interface ListUsersBusinessLogic {
    fun fetchUsers()
    fun searchUsers(request: UserModels.SearchRequest)
}

interface ListUsersPresentable {
    fun presentFetchedUsers(response: ListUsersModels.Response)
    fun presentFetchedUsers(error: DataError)
}

interface ListUsersRoutable : AppRoutable {
    fun showUser(user: ListUsersModels.UserViewModel)
}

// Delegates
interface SelectUserDelegate {
    fun selectUser(user: ListUsersModels.UserViewModel)
}