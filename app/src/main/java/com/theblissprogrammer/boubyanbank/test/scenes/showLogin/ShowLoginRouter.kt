package com.theblissprogrammer.boubyanbank.test.scenes.showLogin

import androidx.fragment.app.Fragment
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common.ShowLoginRoutable
import java.lang.ref.WeakReference

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

class ShowLoginRouter(override var fragment: WeakReference<Fragment?>) : ShowLoginRoutable {

    override fun successfulLogin() {
        dismiss(true)
    }
}