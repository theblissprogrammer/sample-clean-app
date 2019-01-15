package com.theblissprogrammer.boubyanbank.businesslogic.enums

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

/// User defaults keys for strong-typed access.
/// Taken from: https://github.com/radex/SwiftyUserDefaults
open class DefaultsKeys {
    companion object {
        val userID = DefaultsKey<String?>("UserID", "")
    }
}

open class DefaultsKey<out ValueType>(key: String, default: ValueType): DefaultsKeys() {
    val name: String = key
    val type: ValueType? = default
}