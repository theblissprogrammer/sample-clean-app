package com.theblissprogrammer.boubyanbank.test.common.app

import com.theblissprogrammer.boubyanbank.businesslogic.ui.ApplicationService
import com.theblissprogrammer.boubyanbank.businesslogic.ui.CoreApplication
import com.theblissprogrammer.boubyanbank.test.common.app.services.BootApplicationService
import com.theblissprogrammer.boubyanbank.test.common.app.services.CoreApplicationService
import com.theblissprogrammer.boubyanbank.test.common.app.services.DataApplicationService
import com.theblissprogrammer.boubyanbank.test.common.app.services.ErrorApplicationService

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class BoubyanApplication: CoreApplication() {
    companion object {
        lateinit var instance: BoubyanApplication
    }

    init {
        instance = this
    }

    override var services: ArrayList<ApplicationService> = {
        arrayListOf(
            CoreApplicationService(this),
            ErrorApplicationService(),
            BootApplicationService(),
            DataApplicationService()
        )
    }()
}