package com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common

import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.test.common.routers.AppDisplayable
import com.theblissprogrammer.boubyanbank.test.common.routers.AppRoutable
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.ListPostsModels

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface ListPostsDisplayable: AppDisplayable {
    fun displayFetchedPosts(viewModel: ListPostsModels.ViewModel)
}

interface ListPostsBusinessLogic {
    fun fetchPosts(userId: Int)
}

interface ListPostsPresentable {
    fun presentFetchedPosts(response: ListPostsModels.Response)
    fun presentFetchedPosts(error: DataError)
}

interface ListPostsRoutable : AppRoutable {
    fun showPost(id: Int, userId: Int?, userName: String?, companyName: String?)
}

// Delegates
interface SelectPostDelegate {
    fun selectPost(postID: Int)
}