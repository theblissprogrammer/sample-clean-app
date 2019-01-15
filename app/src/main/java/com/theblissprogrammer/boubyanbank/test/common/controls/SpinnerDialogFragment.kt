package com.theblissprogrammer.boubyanbank.test.common.controls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.theblissprogrammer.boubyanbank.test.R
import kotlinx.android.synthetic.main.fragment_dialog_spinner.view.*


/**
* Created by ahmedsaad on 2019-01-14.
* Copyright © 2019. All rights reserved.
*/
class SpinnerDialogFragment: DialogFragment() {

    var activity: AppCompatActivity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pick a style based on the num.
        val style = androidx.fragment.app.DialogFragment.STYLE_NORMAL
        val theme = 0
        setStyle(style, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog?.setCanceledOnTouchOutside(false)

        val rootView = inflater.inflate(R.layout.fragment_dialog_spinner, container, false)
        val message = arguments?.getString("message", "")

        rootView.messageTextView.text = message
        return rootView
    }

    companion object {

        /**
         * Create a new instance of MyDialogFragment, providing "num"
         * as an argument.
         */
        fun newInstance(message: String = "", activity: AppCompatActivity? = null): SpinnerDialogFragment {
            val fragment = SpinnerDialogFragment()
            fragment.activity = activity

            val args = Bundle()
            args.putString("message", message)

            fragment.arguments = args
            fragment.isCancelable = false

            return fragment
        }
    }


}
