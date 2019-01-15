package com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
@Entity
data class Post(
    @PrimaryKey
    val id: Int = -1,
    val userId: Int = -1,
    val title: String = "",
    val body: String? = null)