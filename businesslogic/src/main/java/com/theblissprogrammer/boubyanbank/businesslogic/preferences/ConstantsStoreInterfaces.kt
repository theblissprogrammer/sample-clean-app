package com.theblissprogrammer.boubyanbank.businesslogic.preferences

import com.theblissprogrammer.boubyanbank.businesslogic.R
import com.theblissprogrammer.boubyanbank.businesslogic.enums.Environment

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

interface ConstantsStore {
    fun <T> get(key: Int, default: T): T
}

interface ConstantsType: ConstantsStore {
    val jwtSecretKey: String

    val baseURL: String
        get() = when (Environment.mode) {
            Environment.DEVELOPMENT -> get(R.string.base_url_debug, "" )
            Environment.PRODUCTION -> get(R.string.base_url, "" )
        }

    val baseRESTPath: String
        get() = get(R.string.base_rest_path, "" )

    /// Email of admin used for users
    val emailUserAdmin: String
        get() {
            return when (Environment.mode) {
                Environment.DEVELOPMENT -> get(R.string.email_user_admin_debug, "")
                else -> get(R.string.email_user_admin, "")
            }
        }
}