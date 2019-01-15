package com.theblissprogrammer.boubyanbank.test.common.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.extentions.createSpinner

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
open class BaseFragment: Fragment() {
    protected var isFirstRun: Boolean = false

    val spinner by lazy {
        createSpinner()
    }

    fun setTitle(title: String?) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    fun setSubTitle(title: String?) {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = title
    }

    fun setHomeAsUp(value: Boolean = false) {

        if (value) {
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFirstRun = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.setSupportActionBar(view.findViewById(R.id.toolbar))
    }

    override fun onStop() {
        super.onStop()
        isFirstRun = false
    }

    /**
    Display an alert action in a convenient way.
     **/
    fun present(title: String, message: String? = null,
                includeCancelAction: Boolean = false,
                customView: View? = null,
                positiveHandler: (DialogInterface, Int) -> Unit = { _, _ ->}) {

        activity?.apply {
            this.runOnUiThread {
                val alert = AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(includeCancelAction)
                    .setPositiveButton("OK", positiveHandler)

                if (includeCancelAction) {
                    alert.setNegativeButton("Cancel"){_, _ ->}
                }

                if (customView != null) {
                    alert.setView(customView)
                }

                alert.show()
            }
        }
    }
}