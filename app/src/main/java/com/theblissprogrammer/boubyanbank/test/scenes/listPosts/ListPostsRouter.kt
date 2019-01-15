package com.theblissprogrammer.boubyanbank.test.scenes.listPosts

import androidx.fragment.app.Fragment
import com.theblissprogrammer.boubyanbank.test.common.extentions.replaceFragment
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.ListPostsRoutable
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.ShowPostFragment
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ListPostsRouter(override var fragment: WeakReference<Fragment?>): ListPostsRoutable {

    override fun showPost(id: Int, userId: Int?, userName: String?, companyName: String?) {
        val showPostFragment = ShowPostFragment()
        showPostFragment.id = id
        showPostFragment.userId = userId
        showPostFragment.userName = userName
        showPostFragment.companyName = companyName

        fragment.get()?.replaceFragment(showPostFragment)
    }
}