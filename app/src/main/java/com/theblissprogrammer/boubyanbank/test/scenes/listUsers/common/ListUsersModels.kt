package com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common

import androidx.lifecycle.LiveData
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
sealed class ListUsersModels {
    class Response(
            val users: LiveData<Array<User>>) : ListUsersModels()

    class ViewModel(
            val users: LiveData<List<UserViewModel>>) : ListUsersModels()

    class UserViewModel(
            val id: Int,
            val userName: String,
            val companyName: String,
            val avatarName: String,
            val avatarColor: Int) : ListUsersModels()
}