package com.theblissprogrammer.boubyanbank.test.scenes.listPosts

import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.coroutineOnUi
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostsWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.PostModels
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.ListPostsBusinessLogic
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.ListPostsModels
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.ListPostsPresentable

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ListPostsInteractor(private val presenter: ListPostsPresentable,
                          val postsWorker: PostsWorkerType) : ListPostsBusinessLogic {

    override fun fetchPosts(userId: Int) {
        coroutineOnUi {
            postsWorker.fetch(request = PostModels.Request(userId = userId)) {
                val posts = it.value
                if (posts == null || !it.isSuccess) {
                    this.presenter.presentFetchedPosts(
                        error = it.error
                            ?: DataError.UnknownReason(null)
                    )
                    return@fetch
                }

                this.presenter.presentFetchedPosts(ListPostsModels.Response(posts = posts))
            }
        }
    }

}