package com.theblissprogrammer.boubyanbank.test.common.activities

import android.os.Bundle
import com.theblissprogrammer.boubyanbank.businesslogic.account.AuthenticationWorkerType
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.extentions.replaceFragment
import com.theblissprogrammer.boubyanbank.test.scenes.listUsers.ListUsersFragment
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.ShowLoginFragment

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class MainActivity : BaseActivity() {

    private val authenticationWorker: AuthenticationWorkerType by lazy {
        dependencies.resolveAuthenticationWorker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme_NoActionBar)

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val listUsersFragment = ListUsersFragment()

            replaceFragment(listUsersFragment)
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

    override fun onResume() {
        super.onResume()
        if (!authenticationWorker.isAuthorized) {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        if (!authenticationWorker.isAuthorized) {
            finish()
        }
    }
}
