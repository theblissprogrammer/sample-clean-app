package com.theblissprogrammer.boubyanbank.test.common.routers

import com.theblissprogrammer.boubyanbank.test.common.activities.BaseFragment
import com.theblissprogrammer.boubyanbank.test.common.extentions.hideSpinner

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface AppDisplayable {
    fun display(error: AppModels.Error) {
        when (this) {
            is BaseFragment -> {
                this.present(title = error.title, message = error.message)
            }
            else -> {}
        }
    }

    fun displaySupport(error: AppModels.Error) {
        display(error)
    }
}