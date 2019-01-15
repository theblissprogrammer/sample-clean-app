package com.theblissprogrammer.boubyanbank.businesslogic.common

import android.content.Context
import android.net.ConnectivityManager


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null)
        return false

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}