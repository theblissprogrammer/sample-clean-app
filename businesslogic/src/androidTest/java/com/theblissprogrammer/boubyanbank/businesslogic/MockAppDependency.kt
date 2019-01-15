package com.theblissprogrammer.boubyanbank.businesslogic

import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.AppDependency
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityWorkerType

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class MockAppDependency: AppDependency() {
    override val resolveSecurityWorker: SecurityWorkerType by lazy {
        MockSecurityWorker(resolveSecurityStore)
    }
}