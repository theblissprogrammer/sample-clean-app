package com.theblissprogrammer.boubyanbank.test.common.extentions

import android.app.Activity
import android.content.Context
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.DataResponse
import com.theblissprogrammer.boubyanbank.test.common.app.BoubyanApplication
import com.theblissprogrammer.boubyanbank.test.common.controls.SpinnerDialogFragment


fun AppCompatActivity.replaceFragment(fragment: Fragment) {

    supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_holder, fragment)
            .commitAllowingStateLoss()
}

fun Fragment.replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
    val transaction = fragmentManager
            ?.beginTransaction()
            ?.setCustomAnimations(R.anim.right_enter, R.anim.left_exit, R.anim.left_enter, R.anim.right_exit)
            ?.replace(R.id.fragment_holder, fragment,
                    fragment::class.java.simpleName)

    if (addToBackStack)
        transaction?.addToBackStack(fragment::class.java.simpleName)

    transaction?.commitAllowingStateLoss()
}

fun AppCompatActivity.fullScreen() {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun getString(resId: Int, vararg formatArgs: Any) = BoubyanApplication.instance.getString(resId, *formatArgs)


fun Fragment.createSpinner(message: String = getString(R.string.loading))
        = SpinnerDialogFragment.newInstance(message, activity = activity as? AppCompatActivity)

fun SpinnerDialogFragment.showSpinner(tag: String = "spinner") {
    if(!isAdded || !isVisible)
        activity?.supportFragmentManager?.let {
            show(it, tag)
        }
}

fun SpinnerDialogFragment.hideSpinner() = dismissAllowingStateLoss()

fun Fragment.showSnackBar(response: DataResponse,
                          length: Int = Snackbar.LENGTH_LONG) {

    activity?.apply {
        this.runOnUiThread {
            val snackbar = Snackbar.make(findViewById(android.R.id.content), response.message, length)

            snackbar.setAction(R.string.hide_snackbar) {
                snackbar.dismiss()
            }

            val snackbarView = snackbar.view
            val textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(if (response.isSuccessful)
                ContextCompat.getColor(this, android.R.color.holo_green_light) else
                ContextCompat.getColor(this, android.R.color.holo_red_light))

            snackbar.show()

        }
    }
}

fun Fragment.showSnackBar(message: String,
                          length: Int = Snackbar.LENGTH_LONG) {
    showSnackBar(DataResponse(true, message), length)
}

fun Activity.closeKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}