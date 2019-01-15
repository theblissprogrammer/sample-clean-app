package com.theblissprogrammer.boubyanbank.test.scenes.showPost.common

import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.PostModels
import com.theblissprogrammer.boubyanbank.test.common.routers.AppDisplayable

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface ShowPostDisplayable: AppDisplayable {
    fun displayFetchedPost(viewModel: ShowPostModels.ViewModel)
}

interface ShowPostBusinessLogic {
    fun fetchPost(request: PostModels.PostRequest)
}

interface ShowPostPresentable {
    fun presentFetchedPost(response: ShowPostModels.Response)
    fun presentFetchedPost(error: DataError)
}