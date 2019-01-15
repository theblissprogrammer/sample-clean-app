package com.theblissprogrammer.boubyanbank.businesslogic.extensions

import android.content.SharedPreferences
import com.theblissprogrammer.boubyanbank.businesslogic.enums.DefaultsKey


@Suppress("UNCHECKED_CAST")
fun <T> SharedPreferences.get(key: DefaultsKey<T?>): T? {
    return when (key.type) {
        is String? -> this.getString(key.name, null) as T?
        is Int? -> {
            val value = this.getInt(key.name, -1) as T?
            if (value == -1) null else value
        }
        is Float? -> {
            val value = this.getFloat(key.name, -1F) as T?
            if (value == -1F) null else value
        }
        is Long? -> {
            val value = this.getLong(key.name, -1L) as T?
            if (value == -1L) null else value
        }
        is Boolean? -> this.getBoolean(key.name, false) as T?
        else -> null
    }
}

fun <T> SharedPreferences.put(value: T?, key: DefaultsKey<T?>) {
    val editor = this.edit()
    when (value) {
        is String? -> editor.putString(key.name, value as String)
        is Int? -> editor.putInt(key.name, value as Int)
        is Float? -> editor.putFloat(key.name, value as Float)
        is Long? -> editor.putLong(key.name, value as Long)
        is Boolean? -> editor.putBoolean(key.name, value as Boolean)
        else -> {}
    }
    editor.apply()
}