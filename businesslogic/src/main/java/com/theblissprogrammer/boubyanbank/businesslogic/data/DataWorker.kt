package com.theblissprogrammer.boubyanbank.businesslogic.data

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class DataWorker(private val store: DataStore): DataWorkerType {

    override fun delete(sellerID: String) {
        store.delete(sellerID)
    }

    override fun configure() = store.configure()
}