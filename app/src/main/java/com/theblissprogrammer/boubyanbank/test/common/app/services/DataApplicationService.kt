package com.theblissprogrammer.boubyanbank.test.common.app.services

import com.theblissprogrammer.boubyanbank.businesslogic.data.DataWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.ui.ApplicationService
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.HasDependencies
import com.theblissprogrammer.boubyanbank.businesslogic.protocols.AuthenticationServiceDelegate

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class DataApplicationService: ApplicationService, HasDependencies, AuthenticationServiceDelegate {

    private val dataWorker: DataWorkerType by lazy {
        dependencies.resolveDataWorker
    }

    override fun onCreate() {
        dataWorker.configure()
    }

    override fun authenticationDidLogin(userID: String) {
        dataWorker.configure()
    }

    override fun authenticationDidLogout(userID: String) {

    }
}