package com.theblissprogrammer.boubyanbank.test.scenes.listPosts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.extentions.getString
import com.theblissprogrammer.boubyanbank.test.common.localizedDescription
import com.theblissprogrammer.boubyanbank.test.common.routers.AppModels
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.ListPostsDisplayable
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.ListPostsModels
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.ListPostsPresentable
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ListPostsPresenter(private val fragment: WeakReference<ListPostsDisplayable>?) :
    ListPostsPresentable {

    override fun presentFetchedPosts(response: ListPostsModels.Response) {
        val viewModel = Transformations.switchMap(response.posts) {
            val mutableLiveData: MutableLiveData<List<ListPostsModels.PostViewModel>> = MutableLiveData()

            val posts = it.map {post ->
                ListPostsModels.PostViewModel(
                    id = post.id,
                    title = post.title
                )
            }

            mutableLiveData.value = posts

            mutableLiveData
        }

        fragment?.get()?.displayFetchedPosts(ListPostsModels.ViewModel(posts = viewModel))
    }

    override fun presentFetchedPosts(error: DataError) {
        // Handle and parse error
        val viewModel = AppModels.Error(
                title = getString(R.string.error_title),
                message = error.localizedDescription()
        )

        fragment?.get()?.display(error = viewModel)
    }

}