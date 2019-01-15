package com.theblissprogrammer.boubyanbank.businesslogic.preferences

import android.content.Context

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class ConstantsResourceStore(val context: Context?): ConstantsStore {

    /// Retrieves the constant from the store that corresponds to the given key.
    ///
    /// - Parameter key: The key that is used to read the store item.
    override fun <T> get(key: Int, default: T): T {
        return when (default) {
            is String -> context?.getString(key) as? T ?: default
            else -> default
        }
    }
}