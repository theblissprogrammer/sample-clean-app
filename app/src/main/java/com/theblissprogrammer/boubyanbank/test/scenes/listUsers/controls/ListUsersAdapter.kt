package com.theblissprogrammer.boubyanbank.test.scenes.listUsers.controls

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.ListUsersModels
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.common.SelectUserDelegate
import kotlinx.android.synthetic.main.recycler_view_user.view.*
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

open class ListUsersAdapter(val delegate: WeakReference<SelectUserDelegate?>? = null)
    : RecyclerView.Adapter<ListUsersAdapter.ViewHolder>() {

    private var viewModel: List<ListUsersModels.UserViewModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun getItemCount() = viewModel.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = viewModel[position]

        holder.view.userNameTextView.text = model.userName
        holder.view.companyNameTextView.text = model.companyName
        holder.view.avatarTextView.text = model.avatarName
        holder.view.avatarCardView.setCardBackgroundColor(model.avatarColor)

        holder.view.setOnClickListener { delegate?.get()?.selectUser(model) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_user,
                parent, false)
        return ViewHolder(view)
    }

    fun reloadData(viewModel: List<ListUsersModels.UserViewModel>) {
        this.viewModel = viewModel
    }
}