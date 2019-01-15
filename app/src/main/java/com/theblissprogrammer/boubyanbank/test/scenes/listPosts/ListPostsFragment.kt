package com.theblissprogrammer.boubyanbank.test.scenes.listPosts

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.HasDependencies
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.activities.BaseFragment
import com.theblissprogrammer.boubyanbank.test.common.extentions.hideSpinner
import com.theblissprogrammer.boubyanbank.test.common.extentions.showSpinner
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.*
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.controls.ListPostsAdapter
import kotlinx.android.synthetic.main.fragment_list_posts.*
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ListPostsFragment : BaseFragment(), ListPostsDisplayable, HasDependencies, SelectPostDelegate {

    // VIP variables
    private val interactor: ListPostsBusinessLogic by lazy {
        ListPostsInteractor(
            presenter = ListPostsPresenter(WeakReference(this)),
            postsWorker = dependencies.resolvePostsWorker
        )
    }

    private val router: ListPostsRoutable by lazy {
        ListPostsRouter (
            fragment = WeakReference(this)
        )
    }

    private val adapter by lazy {
        ListPostsAdapter(WeakReference(this))
    }

    //  View models
    var userId: Int? = null
    var userName: String? = null
    var companyName: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (userId == null) activity?.onBackPressed() // Kill fragment if userId is not set

        return inflater.inflate(R.layout.fragment_list_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(userName)
        setSubTitle(companyName)

        setHomeAsUp(true)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = this.adapter

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        userId?.let {
            interactor.fetchPosts(it)
        }
    }

    override fun displayFetchedPosts(viewModel: ListPostsModels.ViewModel) {
        // Create the observer which updates the UI.
        val observer = Observer<List<ListPostsModels.PostViewModel>> {
            adapter.reloadData(it)
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.posts.observe(this, observer)
    }


    override fun selectPost(postID: Int) {
        router.showPost(postID, userId, userName, companyName)
    }
}