package com.theblissprogrammer.boubyanbank.test.scenes.listPosts.controls

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.ListPostsModels
import com.theblissprogrammer.boubyanbank.test.scenes.listPosts.common.SelectPostDelegate
import kotlinx.android.synthetic.main.recycler_view_post.view.*
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
open class ListPostsAdapter(val delegate: WeakReference<SelectPostDelegate?>? = null)
    : RecyclerView.Adapter<ListPostsAdapter.ViewHolder>() {

    private var viewModel: List<ListPostsModels.PostViewModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun getItemCount() = viewModel.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = viewModel[position]

        holder.view.titleTextView.text = model.title

        holder.view.setOnClickListener { delegate?.get()?.selectPost(model.id) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_post,
                parent, false)
        return ViewHolder(view)
    }

    fun reloadData(viewModel: List<ListPostsModels.PostViewModel>) {
        this.viewModel = viewModel
    }
}