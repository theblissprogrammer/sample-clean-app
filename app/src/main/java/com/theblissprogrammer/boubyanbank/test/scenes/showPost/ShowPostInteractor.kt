package com.theblissprogrammer.boubyanbank.test.scenes.showPost

import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineOnUi
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostsWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.PostModels
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.common.ShowPostBusinessLogic
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.common.ShowPostModels
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.common.ShowPostPresentable

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ShowPostInteractor(private val presenter: ShowPostPresentable,
                         val postsWorker: PostsWorkerType) : ShowPostBusinessLogic {

    override fun fetchPost(request: PostModels.PostRequest) {
        coroutineOnUi {
            postsWorker.fetch(request = request) {
                val post = it.value
                if (post == null || !it.isSuccess) {
                    this.presenter.presentFetchedPost(
                        error = it.error
                            ?: DataError.UnknownReason(null)
                    )
                    return@fetch
                }

                this.presenter.presentFetchedPost(ShowPostModels.Response(post = post))
            }
        }
    }

}