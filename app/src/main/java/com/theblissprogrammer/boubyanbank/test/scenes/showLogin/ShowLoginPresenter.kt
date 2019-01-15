package com.theblissprogrammer.boubyanbank.test.scenes.showLogin

import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.extentions.getString
import com.theblissprogrammer.boubyanbank.test.common.localizedDescription
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common.ShowLoginDisplayable
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common.ShowLoginModels
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common.ShowLoginPresentable
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ShowLoginPresenter(private val activity: WeakReference<ShowLoginDisplayable>?) : ShowLoginPresentable {

    override fun presentAuthenticated() {
        activity?.get()?.displayAuthenticated(response = ShowLoginModels.ViewModel())
    }

    override fun presentAuthenticated(error: DataError) {
        // Handle and parse error
        val viewModel = ShowLoginModels.ViewModel().Error(
                title = getString(R.string.login_error_title),
                message = error.localizedDescription()
        )

        activity?.get()?.displayAuthenticated(error = viewModel)
    }

}