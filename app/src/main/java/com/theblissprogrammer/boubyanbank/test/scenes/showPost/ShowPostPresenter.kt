package com.theblissprogrammer.boubyanbank.test.scenes.showPost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.extentions.getString
import com.theblissprogrammer.boubyanbank.test.common.localizedDescription
import com.theblissprogrammer.boubyanbank.test.common.routers.AppModels
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.common.ShowPostDisplayable
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.common.ShowPostModels
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.common.ShowPostPresentable
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ShowPostPresenter(private val fragment: WeakReference<ShowPostDisplayable>?) :
    ShowPostPresentable {

    override fun presentFetchedPost(response: ShowPostModels.Response) {
        val viewModel = Transformations.switchMap(response.post) {
            val mutableLiveData: MutableLiveData<ShowPostModels.PostViewModel> = MutableLiveData()

            val post = ShowPostModels.PostViewModel(
                id = it.id,
                title = it.title,
                description = it.body
            )

            mutableLiveData.value = post

            mutableLiveData
        }

        fragment?.get()?.displayFetchedPost(ShowPostModels.ViewModel(post = viewModel))
    }

    override fun presentFetchedPost(error: DataError) {
        // Handle and parse error
        val viewModel = AppModels.Error(
                title = getString(R.string.error_title),
                message = error.localizedDescription()
        )

        fragment?.get()?.display(error = viewModel)
    }

}