package com.theblissprogrammer.boubyanbank.businesslogic.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.Converters
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostDao
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.Post
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UserDao
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
@Database(
    entities = [
        User::class,
        Post::class
    ],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
}