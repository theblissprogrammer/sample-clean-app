package com.theblissprogrammer.boubyanbank.businesslogic.dependencies

import android.app.Application
import android.content.Context
import com.theblissprogrammer.boubyanbank.businesslogic.account.AuthenticationService
import com.theblissprogrammer.boubyanbank.businesslogic.account.AuthenticationWorker
import com.theblissprogrammer.boubyanbank.businesslogic.account.AuthenticationWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.account.AuthenticationNetworkService
import com.theblissprogrammer.boubyanbank.businesslogic.data.DataRoomStore
import com.theblissprogrammer.boubyanbank.businesslogic.data.DataStore
import com.theblissprogrammer.boubyanbank.businesslogic.data.DataWorker
import com.theblissprogrammer.boubyanbank.businesslogic.data.DataWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.network.*
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.*
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityPreferenceStore
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityStore
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityWorker
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.*
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.*

open class AppDependency: AppDependable {
    override lateinit var application: Application

    override val resolveContext: Context by lazy {
        application.applicationContext
    }

    override val resolveConstants: ConstantsType by lazy {
        Constants(
            store = resolveConstantsStore
        )
    }

    // Workers

    override val resolvePreferencesWorker: PreferencesWorkerType by lazy {
        PreferencesWorker(store = resolvePreferencesStore)
    }

    override val resolveSecurityWorker: SecurityWorkerType by lazy {
        SecurityWorker(
            context = resolveContext,
            store = resolveSecurityStore,
            constants = resolveConstants
        )
    }

    override val resolveDataWorker: DataWorkerType by lazy {
        DataWorker(store = resolveDataStore)
    }

    override val resolveUsersWorker: UsersWorkerType by lazy {
        UsersWorker(
            store = resolveUsersStore,
            cacheStore = resolveUsersCacheStore
        )
    }

    override val resolvePostsWorker: PostsWorkerType by lazy {
        PostsWorker(
            store = resolvePostsStore,
            cacheStore = resolvePostsCacheStore
        )
    }



    // Stores

    override val resolveConstantsStore: ConstantsStore by lazy {
        ConstantsResourceStore(
            context = resolveContext
        )
    }

    override val resolvePreferencesStore: PreferencesStore by lazy {
        PreferencesDefaultsStore(context = resolveContext)
    }

    override val resolveSecurityStore: SecurityStore by lazy {
        SecurityPreferenceStore(context = resolveContext)
    }

    override val resolveDataStore: DataStore by lazy {
        DataRoomStore(
            context = resolveContext,
            preferencesWorker = resolvePreferencesWorker
        )
    }

    override val resolveUsersStore: UsersStore by lazy {
        UsersNetworkStore(
            apiSession = resolveAPISessionService
        )
    }

    override val resolvePostsStore: PostsStore by lazy {
        PostsNetworkStore(
            apiSession = resolveAPISessionService
        )
    }

    // Services

    override val resolveHTTPService: HTTPServiceType by lazy {
        HTTPService()
    }

    override val resolveAPISessionService: APISessionType by lazy {
        APISession(
            context = resolveContext,
            constants = resolveConstants,
            securityWorker = resolveSecurityWorker
        )
    }

    override val resolveAuthenticationWorker: AuthenticationWorkerType  by lazy {
         AuthenticationWorker(
             service = resolveAuthenticationService,
             preferencesWorker = resolvePreferencesWorker,
             securityWorker = resolveSecurityWorker,
             context = resolveContext
        )
    }

    override val resolveAuthenticationService: AuthenticationService  by lazy {
         AuthenticationNetworkService(
             apiSession = resolveAPISessionService
        )
    }

    override val resolveUsersCacheStore: UsersCacheStore by lazy {
        UsersRoomStore(
            userDao = (resolveDataStore as? DataRoomStore)?.instance()?.userDao()
        )
    }

    override val resolvePostsCacheStore: PostsCacheStore by lazy {
        PostsRoomStore(
            postDao = (resolveDataStore as? DataRoomStore)?.instance()?.postDao()
        )
    }
}