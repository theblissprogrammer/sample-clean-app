package com.theblissprogrammer.boubyanbank.businesslogic

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.theblissprogrammer.boubyanbank.businesslogic.data.AppDatabase
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.BusinessLogic
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.HasDependencies
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostDao
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostsCacheStore
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostsStore
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostsWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.Post
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.models.PostModels
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UserDao
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UsersCacheStore
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
@RunWith(AndroidJUnit4::class)
class PostsUnitTests: HasDependencies {
    private lateinit var postDao: PostDao
    private lateinit var userDao: UserDao

    private lateinit var db: AppDatabase

    private val postsWorker: PostsWorkerType by lazy {
        dependencies.resolvePostsWorker
    }

    private val postsRoomStore: PostsCacheStore by lazy {
        dependencies.resolvePostsCacheStore
    }

    private val usersRoomStore: UsersCacheStore by lazy {
        dependencies.resolveUsersCacheStore
    }

    private val postNetworkStore: PostsStore by lazy {
        dependencies.resolvePostsStore
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun configureSdk() {
        BusinessLogic.configure(
            application = InstrumentationRegistry.getTargetContext().applicationContext as Application,
            dependencies = MockAppDependency()
        )
    }

    @Before
    fun createDb() {
        val context: Context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).fallbackToDestructiveMigration().build()

        userDao = db.userDao()
        postDao = db.postDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test_foreign_key() {
        val id = 12
        val userId = 1
        val title = "Testing Title"

        val post = Post(id = id, userId = userId, title = title)

        var constraintFailed = false

        runBlocking {

            val posts = withContext(Dispatchers.IO) {
                try {
                    postDao.insert(post)
                    postDao.fetchByUserSync(userId = userId)
                } catch (e: SQLiteConstraintException) {
                    constraintFailed = true
                }
            }

            Assert.assertTrue("The foreign key should trigger when saving without a valid user object", constraintFailed)
        }
    }

    @Test
    @Throws(Exception::class)
    fun saving_and_fetching_from_db() {
        val id = 12
        val userId = 1
        val title = "Testing Title"

        val post = Post(id = id, userId = userId, title = title)
        val user = User(id = userId)

        runBlocking {

            val posts = withContext(Dispatchers.IO) {
                userDao.insert(user)
                postDao.insert(post)
                postDao.fetchByUserSync(userId = userId)
            }


            Assert.assertEquals("The number of posts must equal to number added.", 1, posts.size)
            Assert.assertEquals("The post id should match after saving to db", id, posts[0].id)
            Assert.assertEquals("The post title should match after saving to db", title, posts[0].title)
        }
    }

    @Test
    @Throws(Exception::class)
    fun saving_and_fetching_live_data() {
        val id = 12
        val userId = 1
        val title = "Testing Title"

        val post = Post(id = id, userId = userId, title = title)
        val user = User(id = userId)

        runBlocking {

            val posts = getValue(withContext(Dispatchers.IO) {
                userDao.insert(user)
                postDao.insert(post)
        postDao.fetchByUser(userId)
            })

            Assert.assertEquals("The number of posts must equal to number added.", 1, posts.size)
            Assert.assertEquals("The post id should match after saving to db", id, posts[0].id)
            Assert.assertEquals("The post title should match after saving to db", title, posts[0].title)
        }
    }

    @Test
    fun fetch_posts_from_network() {
        runBlocking {
            val it = postNetworkStore.fetch(request = PostModels.Request(userId = 1)).await()

            Assert.assertTrue(
                "An error occurred when there should not be: ${it.error?.localizedMessage ?: it.error}",
                it.isSuccess
            )
            Assert.assertNull("Fetch posts should return a null error.", it.error)

            val posts = it.value
            Assert.assertTrue("Fetch users should return valid user posts.", posts?.size ?: 0 > 0)
        }
    }

    @Test
    @Throws(Exception::class)
    fun saving_and_fetching_from_cache_store() {
        val id = 12
        val userId = 1
        val title = "Testing Title"

        val post = Post(id = id, userId = userId, title = title)
        val user = User(id = userId)

        runBlocking {
            usersRoomStore.createOrUpdate(user).await()
            val postCache = postsRoomStore.createOrUpdate(post).await().value

            Assert.assertNotNull("Post should not be null", postCache)

            val postValue = getValue(postCache)
            Assert.assertEquals("The post id should match after saving to db", id, postValue.id)
            Assert.assertEquals("The post title should match after saving to db", title, postValue.title)
        }
    }

    @Test
    @Throws(Exception::class)
    fun fetching_from_worker() {

        runBlocking {
            postsWorker.fetch(request = PostModels.Request(userId = 1)) {
                Assert.assertTrue(
                    "An error occurred when there should not be: ${it.error?.localizedMessage ?: it.error}",
                    it.isSuccess
                )
                Assert.assertNull("Fetching from worker should return a null error.", it.error)

                val posts = getValue(it.value)
                Assert.assertTrue("Fetch users should return valid posts object", posts.isNotEmpty())
            }
        }
    }

}
