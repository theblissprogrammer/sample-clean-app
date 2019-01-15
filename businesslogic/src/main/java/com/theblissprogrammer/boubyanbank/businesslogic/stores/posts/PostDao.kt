package com.theblissprogrammer.boubyanbank.businesslogic.stores.posts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.theblissprogrammer.boubyanbank.businesslogic.stores.common.CommonDao
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.Post

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
@Dao
interface PostDao: CommonDao<Post> {
    @Query("SELECT * FROM Post")
    fun fetchAllPosts(): LiveData<Array<Post>>

    @Query("SELECT * FROM Post WHERE userId = :userId")
    fun fetchByUser(userId: Int): LiveData<Array<Post>>

    @Query("SELECT * FROM Post WHERE userId = :userId AND id = :id")
    fun fetchByUser(id: Int, userId: Int): LiveData<Post>

    @Query("SELECT * FROM Post WHERE id = :id")
    fun fetch(id: Int): LiveData<Post>

    // Used for sync calls

    @Query("SELECT * FROM Post")
    fun fetchAllPostsSync(): Array<Post>

    @Query("SELECT * FROM Post WHERE userId = :userId")
    fun fetchByUserSync(userId: Int): Array<Post>
}