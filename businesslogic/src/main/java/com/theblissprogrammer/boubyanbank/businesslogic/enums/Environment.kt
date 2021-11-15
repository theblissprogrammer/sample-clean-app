package com.theblissprogrammer.boubyanbank.businesslogic.enums

import com.theblissprogrammer.boubyanbank.businesslogic.BuildConfig


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

enum class Environment {
    DEVELOPMENT,
    PRODUCTION;
    companion object {
        var mode: Environment = {
            if (BuildConfig.BUILD_TYPE.equals("debug"))
                DEVELOPMENT
            else
                PRODUCTION
        }()
    }
}