package com.theblissprogrammer.boubyanbank.businesslogic.extensions

import com.theblissprogrammer.boubyanbank.businesslogic.common.Result
import com.theblissprogrammer.boubyanbank.businesslogic.common.Result.Companion.failure
import com.theblissprogrammer.boubyanbank.businesslogic.common.CompletionResponse
import com.theblissprogrammer.boubyanbank.businesslogic.common.LiveResult
import com.theblissprogrammer.boubyanbank.businesslogic.errors.DataError
import kotlinx.coroutines.*
import java.io.IOException

/**
 * Created by ahmedsaad on 2019-01-14.
 */
fun <T> coroutineCompletionOnUi (completion: CompletionResponse<T>? = null, call: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        try {
            call()
        } catch (e: IOException){
            completion?.invoke(failure(
                    DataError.NetworkFailure(e)
            ))
        }
    }
}

fun <T> coroutineNetwork (call: suspend () -> Result<T>): Deferred<Result<T>> {
    return GlobalScope.async(Dispatchers.IO) {
        call()
    }
}

fun <T> coroutineRoom (call: () -> LiveResult<T>): Deferred<LiveResult<T>> {
    return GlobalScope.async(Dispatchers.IO) {
        call()
    }
}

fun coroutineOnUi (call: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        call()
    }
}

fun coroutine (call: suspend () -> Unit): Deferred<Unit> {
    return GlobalScope.async {
        call()
    }
}