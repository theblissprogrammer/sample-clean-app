package com.theblissprogrammer.boubyanbank.businesslogic.ui

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.theblissprogrammer.boubyanbank.businesslogic.extensions.*
import com.theblissprogrammer.boubyanbank.businesslogic.protocols.AuthenticationServiceDelegate
import com.theblissprogrammer.boubyanbank.businesslogic.protocols.PushNotificationServiceDelegate


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
open class CoreApplication: PluggableApplication() {
    private val broadcastReceiver = CoreBroadcastReceiver()

    override fun onCreate() {
        super.onCreate()

        val filter = IntentFilter()

        if (services.any { it is AuthenticationServiceDelegate }) {
            filter.addAction(ACTION_AUTHENTICATION_DID_LOGIN)
            filter.addAction(ACTION_AUTHENTICATION_DID_LOGOUT)
        }

        if (services.any { it is PushNotificationServiceDelegate }) {
            filter.addAction(ACTION_REFRESHED_DEVICE_TOKEN)
        }

        LocalBroadcastManager.getInstance(this.applicationContext)
                .registerReceiver(broadcastReceiver, filter)
    }

    override fun onTerminate() {
        super.onTerminate()
        LocalBroadcastManager.getInstance(this.applicationContext)
                .unregisterReceiver(broadcastReceiver)
    }

    fun authenticationDidLogin(userID: String?) {
        if (userID.isNullOrBlank()) return
        services.mapNotNull { it as? AuthenticationServiceDelegate }
                .forEach { it.authenticationDidLogin(userID = userID) }
    }

    fun authenticationDidLogout(userID: String?) {
        if (userID.isNullOrBlank()) return
        services.mapNotNull { it as? AuthenticationServiceDelegate }
                .forEach { it.authenticationDidLogout(userID = userID) }
    }

    fun onTokenRefresh(deviceToken: String?) {
        if (deviceToken.isNullOrBlank()) return
        services.mapNotNull { it as? PushNotificationServiceDelegate }
                .forEach { it.onTokenRefresh(deviceToken = deviceToken) }
    }

    inner class CoreBroadcastReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                ACTION_AUTHENTICATION_DID_LOGIN -> authenticationDidLogin(intent.getStringExtra(USER_ID))
                ACTION_AUTHENTICATION_DID_LOGOUT -> authenticationDidLogout(intent.getStringExtra(USER_ID))
                ACTION_REFRESHED_DEVICE_TOKEN -> onTokenRefresh(intent.getStringExtra(DEVICE_TOKEN))
            }
        }
    }
}