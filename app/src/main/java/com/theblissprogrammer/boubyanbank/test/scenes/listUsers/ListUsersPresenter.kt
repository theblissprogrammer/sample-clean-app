package com.theblissprogrammer.boubyanbank.test.scenes.listUsers

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.extentions.getString
import com.theblissprogrammer.boubyanbank.test.common.localizedDescription
import com.theblissprogrammer.boubyanbank.test.common.routers.AppModels
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.ListUsersDisplayable
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.ListUsersModels
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.ListUsersPresentable
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ListUsersPresenter(private val fragment: WeakReference<ListUsersDisplayable>?) :
    ListUsersPresentable {

    override fun presentFetchedUsers(response: ListUsersModels.Response) {
        val viewModel = Transformations.switchMap(response.users) {
            val mutableLiveData: MutableLiveData<List<ListUsersModels.UserViewModel>> = MutableLiveData()
            val users = it.map {user ->
                val r = Random()
                val red = r.nextInt(255 - 0 + 1) + 0
                val green = r.nextInt(255 - 0 + 1) + 0
                val blue = r.nextInt(255 - 0 + 1) + 0

                ListUsersModels.UserViewModel(
                    id = user.id,
                    userName = user.name,
                    companyName = user.company.name,
                    avatarName = user.username[0].toString(),
                    avatarColor = Color.rgb(red, green, blue)
                )
            }

            mutableLiveData.value = users

            mutableLiveData
        }

        fragment?.get()?.displayFetchedUsers(ListUsersModels.ViewModel(users = viewModel))
    }

    override fun presentFetchedUsers(error: DataError) {
        // Handle and parse error
        val viewModel = AppModels.Error(
                title = getString(R.string.error_title),
                message = error.localizedDescription()
        )

        fragment?.get()?.display(error = viewModel)
    }

}