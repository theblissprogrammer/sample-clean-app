package com.theblissprogrammer.boubyanbank.businesslogic

import com.theblissprogrammer.boubyanbank.businesslogic.enums.SecurityProperty
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityStore
import com.theblissprogrammer.boubyanbank.businesslogic.security.SecurityWorkerType
import org.json.JSONObject

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class MockSecurityWorker(private val store: SecurityStore): SecurityWorkerType {
    override fun set(token: String, email: String, password: String): Boolean {
        val payload = JSONObject().put("authentication_token", token).put("email", email)

        val jwt = jwt(payload = payload) ?: return false

        return set(key = SecurityProperty.JWT, value = jwt)
                && set(key = SecurityProperty.TOKEN, value = token)
                && set(key = SecurityProperty.EMAIL, value = email)
                && set(key = SecurityProperty.PASSWORD, value = password)
    }

    override fun jwt(payload: JSONObject): String? {
        return ""
    }

    override fun get(key: SecurityProperty): String? {
        return store.get(key)
    }

    override fun set(key: SecurityProperty, value: String?): Boolean {
        return store.set(key, value = value)
    }

    override fun delete(key: SecurityProperty): Boolean {
        return store.delete(key)
    }

    override fun clear() {
        store.clear()
    }

    // For testing purposes we assume Security worker is functioning.
    override fun createKey(alias: String) { }
    override fun deleteKey(alias: String) { }
    override fun encrypt(value: String?): String? { return null }
    override fun decrypt(value: String?): String? { return null }

}