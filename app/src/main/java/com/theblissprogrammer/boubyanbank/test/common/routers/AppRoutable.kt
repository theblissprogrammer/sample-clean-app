package com.theblissprogrammer.boubyanbank.test.common.routers

import androidx.fragment.app.Fragment
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.ShowLoginFragment
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common.AuthenticationDelegate
import com.theblissprogrammer.boubyanbank.test.common.extentions.replaceFragment
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface AppRoutable {
    var fragment: WeakReference<Fragment?>

    /// Display the login with a delegate subscription
    fun showLogin(delegate: WeakReference<AuthenticationDelegate?>? = null) {
        val showLoginFragment = ShowLoginFragment()
        showLoginFragment.authenticationDelegate = delegate

        fragment.get()?.replaceFragment(showLoginFragment)
    }

    fun dismiss(isFragment: Boolean = false) {
        if (isFragment) {
            fragment.get()?.fragmentManager?.popBackStack() ?: fragment.get()?.activity?.finish()
        } else {
            fragment.get()?.activity?.finish()
        }
    }
}