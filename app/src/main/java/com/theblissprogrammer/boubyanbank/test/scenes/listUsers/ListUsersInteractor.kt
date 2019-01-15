package com.theblissprogrammer.boubyanbank.test.scenes.listUsers

import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineOnUi
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UsersWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.UserModels
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.ListUsersBusinessLogic
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.ListUsersModels
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.ListUsersPresentable

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ListUsersInteractor(private val presenter: ListUsersPresentable,
                          val usersWorker: UsersWorkerType) : ListUsersBusinessLogic {

    override fun fetchUsers() {
        coroutineOnUi {
            usersWorker.fetch(request = UserModels.Request()) {
                val users = it.value
                if (users == null || !it.isSuccess) {
                    this.presenter.presentFetchedUsers(
                        error = it.error
                            ?: DataError.UnknownReason(null)
                    )
                    return@fetch
                }

                this.presenter.presentFetchedUsers(ListUsersModels.Response(users = users))
            }
        }
    }

    override fun searchUsers(request: UserModels.SearchRequest) {
        coroutineOnUi {
            usersWorker.fetch(request = request) {
                val users = it.value
                if (users == null || !it.isSuccess) {
                    this.presenter.presentFetchedUsers(
                        error = it.error
                            ?: DataError.UnknownReason(null)
                    )
                    return@fetch
                }

                this.presenter.presentFetchedUsers(ListUsersModels.Response(users = users))
            }
        }
    }

}