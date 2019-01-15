package com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models

import androidx.room.ColumnInfo


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
data class Company (
    @ColumnInfo(name = "companyName")
    val name: String = "",
    @ColumnInfo(name = "companyCatchPhrase")
    val catchPhrase: String? = null,
    @ColumnInfo(name = "companyBs")
    val bs: String? = null
)