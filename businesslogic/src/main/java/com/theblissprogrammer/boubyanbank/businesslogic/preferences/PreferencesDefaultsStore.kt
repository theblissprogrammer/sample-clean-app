package com.theblissprogrammer.boubyanbank.businesslogic.preferences

import android.content.Context
import android.content.SharedPreferences
import com.theblissprogrammer.boubyanbank.businesslogic.enums.DefaultsKey
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.get
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.put

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

class PreferencesDefaultsStore(val context: Context?): PreferencesStore {
    private val defaults: SharedPreferences? = context?.
            getSharedPreferences("user_defaults", Context.MODE_PRIVATE)


    /// Retrieves the value from user defaults that corresponds to the given key.
    ///
    /// - Parameter key: The key that is used to read the user defaults item.
    override fun <T> get(key: DefaultsKey<T?>): T? {
        return defaults?.get(key)
    }

    /// Stores the value in the user defaults item under the given key.
    ///
    /// - Parameters:
    ///   - value: Value to be written to the user defaults.
    ///   - key: Key under which the value is stored in the user defaults.
    override fun <T> set(value: T?, key: DefaultsKey<T?>) {
        defaults?.put(value = value, key = key)
    }

    /// Deletes the single user defaults item specified by the key.
    ///
    /// - Parameter key: The key that is used to delete the keychain item.
    /// - Returns: True if the item was successfully deleted.
    override fun <T> remove(key: DefaultsKey<T?>) {
        val editor = defaults?.edit()
        editor?.remove(key.name)
        editor?.apply()
    }

    /// Removes all the user defaults items.
    override fun clear() {
        val editor = defaults?.edit()
        editor?.clear()
        editor?.apply()
    }

}