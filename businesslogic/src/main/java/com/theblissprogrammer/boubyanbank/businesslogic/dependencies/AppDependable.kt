package com.theblissprogrammer.boubyanbank.businesslogic.dependencies

import android.app.Application
import android.content.Context
import com.theblissprogrammer.boubyanbank.businesslogic.account.AuthenticationService
import com.theblissprogrammer.boubyanbank.businesslogic.account.AuthenticationWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.data.DataStore
import com.theblissprogrammer.boubyanbank.businesslogic.data.DataWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.network.APISessionType
import com.theblissprogrammer.boubyanbank.businesslogic.network.HTTPServiceType
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.ConstantsStore
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.ConstantsType
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.PreferencesStore
import com.theblissprogrammer.boubyanbank.businesslogic.preferences.PreferencesWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityStore
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostsCacheStore
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostsStore
import com.theblissprogrammer.boubyanbank.businesslogic.stores.posts.PostsWorkerType
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UsersCacheStore
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UsersStore
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.UsersWorkerType


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface AppDependable {
    var application: Application

    val resolveContext: Context

    val resolveConstants: ConstantsType

    val resolveDataWorker: DataWorkerType
    val resolvePreferencesWorker: PreferencesWorkerType
    val resolveSecurityWorker: SecurityWorkerType
    val resolveAuthenticationWorker: AuthenticationWorkerType
    val resolveUsersWorker: UsersWorkerType
    val resolvePostsWorker: PostsWorkerType

    val resolveConstantsStore: ConstantsStore
    val resolvePreferencesStore: PreferencesStore
    val resolveSecurityStore: SecurityStore
    val resolveDataStore: DataStore
    val resolveUsersStore: UsersStore
    val resolvePostsStore: PostsStore

    val resolveHTTPService: HTTPServiceType
    val resolveAPISessionService: APISessionType

    val resolveAuthenticationService: AuthenticationService

    val resolveUsersCacheStore: UsersCacheStore
    val resolvePostsCacheStore: PostsCacheStore
}