package com.theblissprogrammer.boubyanbank.businesslogic.common

import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import com.theblissprogrammer.boubyanbank.businesslogic.errors.NetworkError


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

class NoInternetException: Exception()

fun initDataError(error: NetworkError?): DataError {
    // Handle no internet
    if (error?.internalError is NoInternetException) {
        return DataError.NoInternet
    }

    // Handle by status code
    return when (error?.statusCode) {
        400 -> DataError.NetworkFailure(error)
        401 -> DataError.Unauthorized
        403 -> DataError.Forbidden
        else -> DataError.Other(error?.fieldErrors ?: mutableMapOf())
    }
}