package com.theblissprogrammer.boubyanbank.businesslogic.errors

/**
 * Created by ahmedsaad on 2019-01-14.
 */

sealed class DataError : Exception() {
    object DuplicateFailure : DataError()
    object NonExistent : DataError()
    object Incomplete : DataError()
    object Unauthorized : DataError()
    object NoInternet : DataError()
    object Forbidden : DataError()
    object BadRequest : DataError()
    object InvalidEmail : DataError()
    object PasswordMismatch : DataError()
    object PasswordStrength : DataError()
    class ParseFailure(var error: Exception?) : DataError()
    class DatabaseFailure(var error: Exception?) : DataError()
    class CacheFailure(var error: Exception?) : DataError()
    class NetworkFailure(var error: Exception?) : DataError()
    class UnknownReason(var error: Exception?) : DataError()
    class Other(var errors: FieldErrors) : DataError()
}