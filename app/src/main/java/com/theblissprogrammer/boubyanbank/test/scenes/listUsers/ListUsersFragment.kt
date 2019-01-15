package com.theblissprogrammer.boubyanbank.test.scenes.listUsers

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.HasDependencies
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.UserModels
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.activities.BaseFragment
import com.theblissprogrammer.boubyanbank.test.common.controls.SearchAnimationToolbar
import com.theblissprogrammer.boubyanbank.test.common.extentions.closeKeyboard
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.*
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.controls.ListUsersAdapter
import kotlinx.android.synthetic.main.fragment_list_users.*
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ListUsersFragment : BaseFragment(), ListUsersDisplayable, HasDependencies, SelectUserDelegate {

    // VIP variables
    private val interactor: ListUsersBusinessLogic by lazy {
        ListUsersInteractor(
            presenter = ListUsersPresenter(WeakReference(this)),
            usersWorker = dependencies.resolveUsersWorker
        )
    }

    private val router: ListUsersRoutable by lazy {
        ListUsersRouter (
            fragment = WeakReference(this)
        )
    }

    private val adapter by lazy {
        ListUsersAdapter(WeakReference(this))
    }

    //  View models

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setTitle(getString(R.string.list_users_title))

        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = this.adapter

        loadListeners()
        
        val context = context ?: return
        searchAnimationToolbar.setSearchTextColor(ContextCompat.getColor(context, R.color.darkBlue))
        searchAnimationToolbar.setSearchHintColor(ContextCompat.getColor(context, R.color.greyishBrown))
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }


    override fun onPause() {
        super.onPause()
        searchAnimationToolbar.onBackPressed()
    }

    private fun loadData() {
        interactor.fetchUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item?.itemId


        if (id == R.id.action_search) {
            searchAnimationToolbar.onSearchIconClick()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadListeners() {
        searchAnimationToolbar.setOnSearchQueryChangedListener(object : SearchAnimationToolbar.OnSearchQueryChangedListener {
            override fun onSearchCollapsed() {

            }

            override fun onSearchQueryChanged(query: String) {
                interactor.searchUsers(
                    request = UserModels.SearchRequest(
                        query = query
                    )
                )
            }

            override fun onSearchExpanded() {

            }

            override fun onSearchSubmitted(query: String) {
                interactor.searchUsers(
                    request = UserModels.SearchRequest(
                        query = query
                    )
                )

                activity?.closeKeyboard()
            }
        })
    }

    override fun displayFetchedUsers(viewModel: ListUsersModels.ViewModel) {
        // Create the observer which updates the UI.
        val observer = Observer<List<ListUsersModels.UserViewModel>> {
            adapter.reloadData(it)
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.users.observe(this, observer)
    }


    override fun selectUser(user: ListUsersModels.UserViewModel) {
        router.showUser(user)
    }
}