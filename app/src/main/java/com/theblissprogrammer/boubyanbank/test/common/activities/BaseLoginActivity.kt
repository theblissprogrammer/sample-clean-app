package com.theblissprogrammer.boubyanbank.test.common.activities

import android.os.Bundle
import com.theblissprogrammer.boubyanbank.test.common.extentions.replaceFragment
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.ShowLoginFragment


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class BaseLoginActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val showLoginFragment = ShowLoginFragment()
            showLoginFragment.routedFromLoginActivity = true

            replaceFragment(showLoginFragment)
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}