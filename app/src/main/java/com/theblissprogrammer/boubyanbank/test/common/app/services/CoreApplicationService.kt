package com.theblissprogrammer.boubyanbank.test.common.app.services

import android.app.Application
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.BusinessLogic
import com.theblissprogrammer.boubyanbank.businesslogic.ui.ApplicationService

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class CoreApplicationService(val application: Application): ApplicationService {

    override fun onCreate() {
        BusinessLogic.configure(application = application)
    }
}