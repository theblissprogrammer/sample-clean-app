package com.theblissprogrammer.boubyanbank.businesslogic.stores.common

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface CommonDao<T> {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: T)

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun update(entity: T)

    @Update
    fun update(vararg entity: T)

    @Delete
    fun delete(vararg entity: T)
}

fun <T>CommonDao<T>.insertOrUpdate(entity: T) {
    try {
        insert(entity)
    } catch (exception: SQLiteConstraintException) {
        update(entity)
    }
}