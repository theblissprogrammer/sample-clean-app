package com.theblissprogrammer.boubyanbank.businesslogic.dependencies

import android.app.Application

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class BusinessLogic {
    companion object {
        fun configure(application: Application, dependencies: AppDependable = AppDependency()) {
            dependencies.application = application
            DependencyInjector.dependencies = dependencies
        }
    }
}