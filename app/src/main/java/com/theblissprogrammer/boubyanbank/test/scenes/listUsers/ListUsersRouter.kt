package com.theblissprogrammer.boubyanbank.test.scenes.listUsers

import androidx.fragment.app.Fragment
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import com.theblissprogrammer.boubyanbank.test.common.extentions.replaceFragment
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.ListPostsFragment
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.ListUsersModels
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.ListUsersRoutable
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ListUsersRouter(override var fragment: WeakReference<Fragment?>): ListUsersRoutable {

    override fun showUser(user: ListUsersModels.UserViewModel) {
        val listPostsFragment = ListPostsFragment()
        listPostsFragment.userId = user.id
        listPostsFragment.userName = user.userName
        listPostsFragment.companyName = user.companyName

        fragment.get()?.replaceFragment(listPostsFragment)
    }
}