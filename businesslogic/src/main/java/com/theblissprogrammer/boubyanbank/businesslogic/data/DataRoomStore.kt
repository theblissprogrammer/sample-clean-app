package com.theblissprogrammer.boubyanbank.businesslogic.data

import android.content.Context
import androidx.room.Room
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.PreferencesWorkerType

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class DataRoomStore(val context: Context, override val preferencesWorker: PreferencesWorkerType): DataStore {
    fun instance(name: String = this.name): AppDatabase?{
        return databases[name]
    }

    private var databases: MutableMap<String, AppDatabase> = mutableMapOf()

    init {
        this.configure()
    }

    override fun configure() {
        if (instance() == null) {
            databases[this.name] = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, this.name
            ).fallbackToDestructiveMigration().build()
        }
    }

    override fun delete(sellerID: String) {
        instance(generateName(sellerID))?.clearAllTables()
        databases.remove(generateName(sellerID))
    }
}