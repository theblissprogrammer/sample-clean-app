package com.theblissprogrammer.boubyanbank.businesslogic.stores.users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.theblissprogrammer.boubyanbank.businesslogic.stores.common.CommonDao
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
 @Dao
interface UserDao: CommonDao<User> {
    @Query("SELECT * FROM User")
    fun fetchAllUsers(): LiveData<Array<User>>

    @Query("SELECT * FROM User WHERE name LIKE :query")
    fun search(query: String): LiveData<Array<User>>

    @Query("SELECT * FROM User WHERE id IN (:ids)")
    fun fetch(ids: Array<Int>): LiveData<Array<User>>

    @Query("SELECT * FROM User WHERE id = :id")
    fun fetch(id: Int): LiveData<User>

    // Used for sync calls

    @Query("SELECT * FROM User")
    fun fetchAllUsersSync(): Array<User>

    @Query("SELECT * FROM User WHERE id IN (:ids)")
    fun fetchSync(ids: Array<Int>): Array<User>

    @Query("SELECT * FROM User WHERE id = :id")
    fun fetchSync(id: Int): User
}