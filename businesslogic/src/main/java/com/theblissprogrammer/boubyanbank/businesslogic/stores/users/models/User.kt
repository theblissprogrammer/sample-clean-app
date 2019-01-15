package com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
@Entity
data class User (
    @PrimaryKey
    val id: Int = -1,
    val name: String = "",
    val username: String = "",
    val email: String? = null,
    @Embedded
    val address: Address? = null,
    val phone: String? = null,
    val website: String? = null,
    @Embedded
    val company: Company = Company()
)
 