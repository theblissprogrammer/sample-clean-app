package com.theblissprogrammer.boubyanbank.test.scenes.showPost

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.HasDependencies
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.PostModels
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.activities.BaseFragment
import com.theblissprogrammer.boubyanbank.test.common.extentions.hideSpinner
import com.theblissprogrammer.boubyanbank.test.common.extentions.showSpinner
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.*
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.common.ShowPostBusinessLogic
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.common.ShowPostDisplayable
import com.theblissprogrammer.boubyanbank.test.scenes.showPost.common.ShowPostModels
import kotlinx.android.synthetic.main.fragment_list_posts.*
import kotlinx.android.synthetic.main.fragment_show_post.*
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ShowPostFragment : BaseFragment(), ShowPostDisplayable, HasDependencies {

    // VIP variables
    private val interactor: ShowPostBusinessLogic by lazy {
        ShowPostInteractor(
            presenter = ShowPostPresenter(WeakReference(this)),
            postsWorker = dependencies.resolvePostsWorker
        )
    }

    //  View models
    var userId: Int? = null
    var id: Int? = null
    var userName: String? = null
    var companyName: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (userId == null || id == null) activity?.onBackPressed() // Kill fragment if userId is not set

        return inflater.inflate(R.layout.fragment_show_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(userName)
        setSubTitle(companyName)

        setHomeAsUp(true)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        spinner.showSpinner()

        val userId = userId ?: return
        val id = id ?: return

        interactor.fetchPost(PostModels.PostRequest(userId = userId, id = id))
    }

    override fun displayFetchedPost(viewModel: ShowPostModels.ViewModel) {
        // Create the observer which updates the UI.
        val observer = Observer<ShowPostModels.PostViewModel> {
            if (it.title.isNotEmpty())
                spinner.hideSpinner()

            titleTextView.text = it.title
            descriptionTextView.text = it.description
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.post.observe(this, observer)
    }
}