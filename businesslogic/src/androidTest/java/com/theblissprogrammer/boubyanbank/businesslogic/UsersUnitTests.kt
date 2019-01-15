package com.theblissprogrammer.boubyanbank.businesslogic

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.theblissprogrammer.boubyanbank.businesslogic.data.AppDatabase
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.BusinessLogic
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.HasDependencies
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UserDao
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UsersCacheStore
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UsersStore
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UsersWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.Company
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.User
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.UserModels
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
class UsersUnitTests: HasDependencies {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    private val usersWorker: UsersWorkerType by lazy {
        dependencies.resolveUsersWorker
    }

    private val usersRoomStore: UsersCacheStore by lazy {
        dependencies.resolveUsersCacheStore
    }

    private val usersNetworkStore: UsersStore by lazy {
        dependencies.resolveUsersStore
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
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saving_and_fetching_from_db() {
        val id = 12
        val companyName = "Testing Company"

        val user = User(id = id, company = Company(name = companyName))

        runBlocking {

            val users = withContext(Dispatchers.IO) {
                userDao.insert(user)
                userDao.fetchSync(id = id)
            }

            Assert.assertEquals("The user ids should match after saving to db", id, users.id)
            Assert.assertEquals("The user company name should match after saving to db", companyName, users.company.name)
        }
    }

    @Test
    @Throws(Exception::class)
    fun saving_and_fetching_live_data() {
        val id = 1234
        val companyName = "Testing Company"

        val user = User(id = id, company = Company(name = companyName))

        runBlocking {

            val users = getValue(withContext(Dispatchers.IO) {
                userDao.insert(user)
        userDao.fetchAllUsers()
            })

            Assert.assertEquals("The number of users must equal to number added.", 1, users.size)
            Assert.assertEquals("The user id should match after saving to db", id, users[0].id)
            Assert.assertEquals("The user company name should match after saving to db", companyName, users[0].company.name)
        }
    }

    @Test
    @Throws(Exception::class)
    fun saving_and_fetching_from_cache_store() {
        val id = 1234
        val companyName = "Testing Company"

        val user = User(id = id, company = Company(name = companyName))

        runBlocking {
            val userCache = usersRoomStore.createOrUpdate(user).await().value

            Assert.assertNotNull("user should not be null", userCache)

            val userValue = getValue(userCache)
            Assert.assertEquals("The user id should match after saving to db", id, userValue.id)
            Assert.assertEquals("The user company name should match after saving to db", companyName, userValue.company.name)
        }
    }

    @Test
    fun fetch_users_from_network() {
        runBlocking {
            val it = usersNetworkStore.fetch(request = UserModels.Request()).await()

            Assert.assertTrue(
                "An error occurred when there should not be: ${it.error?.localizedMessage ?: it.error}",
                it.isSuccess
            )
            Assert.assertNull("Fetch users should return a null error.", it.error)

            val users = it.value
            Assert.assertTrue("Fetch users should return valid user objects.", users?.size ?: 0 > 0)
        }
    }

    @Test
    @Throws(Exception::class)
    fun saving_and_fetching_from_worker() {
        val id = 1234
        val companyName = "Testing Company"

        val user = User(id = id, company = Company(name = companyName))

        runBlocking {
            usersRoomStore.createOrUpdate(user).await()

            usersWorker.fetch(request = UserModels.Request(
                id = id
            )) {
                Assert.assertTrue(
                    "An error occurred when there should not be: ${it.error?.localizedMessage ?: it.error}",
                    it.isSuccess
                )
                Assert.assertNull("Fetching from worker should return a null error.", it.error)

                val user = getValue(it.value)
                Assert.assertNotNull("Login should return valid user object.", user)
                Assert.assertEquals("The user id should match after saving to db", id, user[0].id)
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun fetching_from_worker() {

        runBlocking {
            usersWorker.fetch(request = UserModels.Request()) {
                Assert.assertTrue(
                    "An error occurred when there should not be: ${it.error?.localizedMessage ?: it.error}",
                    it.isSuccess
                )
                Assert.assertNull("Fetching from worker should return a null error.", it.error)

                val users = getValue(it.value)
                Assert.assertTrue("Fetch users should return valid user objects.", users?.size ?: 0 > 0)
            }
        }
    }

}
