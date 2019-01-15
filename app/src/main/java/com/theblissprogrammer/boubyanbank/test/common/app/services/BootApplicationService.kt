package com.theblissprogrammer.boubyanbank.test.common.app.services

import android.app.Activity
import android.content.Intent
import com.theblissprogrammer.boubyanbank.businesslogic.account.AuthenticationWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.HasDependencies
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.ConstantsType
import com.theblissprogrammer.boubyanbank.businesslogic.protocols.AuthenticationServiceDelegate
import com.theblissprogrammer.boubyanbank.businesslogic.ui.ApplicationService
import com.theblissprogrammer.boubyanbank.test.common.activities.BaseLoginActivity
import com.theblissprogrammer.boubyanbank.test.common.activities.MainActivity

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class BootApplicationService: ApplicationService, HasDependencies, AuthenticationServiceDelegate {

    private val authenticationWorker: AuthenticationWorkerType by lazy {
        dependencies.resolveAuthenticationWorker
    }

    private val context by lazy {
        dependencies.resolveContext
    }

    override fun onCreate() {
        // Linked to MainActivity onResume & onPause
        if (!authenticationWorker.isAuthorized) {
            val intent = Intent(context, BaseLoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun authenticationDidLogin(userID: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun authenticationDidLogout(userID: String) {
        val intent = Intent(context, BaseLoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}