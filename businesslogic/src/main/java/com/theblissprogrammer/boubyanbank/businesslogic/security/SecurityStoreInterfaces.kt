package com.theblissprogrammer.boubyanbank.businesslogic.security

import com.theblissprogrammer.boubyanbank.businesslogic.enums.SecurityProperty
import org.json.JSONObject

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

interface SecurityStore {
    fun get(key: SecurityProperty): String?
    fun set(key: SecurityProperty, value: String?): Boolean
    fun delete(key: SecurityProperty): Boolean
    fun clear()
}

interface SecurityWorkerType: SecurityStore {
    fun set(token: String, email: String, password: String): Boolean
    fun jwt(payload: JSONObject): String?

    fun createKey(alias: String)
    fun deleteKey(alias: String)
    fun encrypt(value: String?): String?
    fun decrypt(value: String?): String?
}