package com.theblissprogrammer.boubyanbank.businesslogic.data

import com.theblissprogrammer.boubyanbank.businesslogic.enums.DefaultsKeys.Companion.userID
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.PreferencesWorkerType

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface DataStore {
    val preferencesWorker: PreferencesWorkerType

    /// Name for isolated database per user or use anonymously
    val name: String
        get() = generateName(preferencesWorker.get(userID) ?: "")

    /// Used for referencing databases not associated with the current user
    fun generateName(sellerID: String): String {
        return "user_$sellerID"
    }

    fun configure()
    fun delete(sellerID: String)
}

interface DataWorkerType {
    fun delete(sellerID: String)
    fun configure()
}