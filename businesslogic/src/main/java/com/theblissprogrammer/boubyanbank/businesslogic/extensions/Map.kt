package com.theblissprogrammer.boubyanbank.businesslogic.extensions

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
/// Keys to scrub
var scrubKeys: List<String> =  listOf(
        "authorization",
        "set-cookie",
        "user_email",
        "password",
        "first_name",
        "last_name",
        "email",
        "phone_number",
        "profile_picture_url",
        "token")

/// Remove sensitive info from headers
val <k, v> Map<k, v>.scrubbed: Map<String, String>
    get() {
        val map = hashMapOf<String, String>()
        this.forEach {
            val key = "${it.key}"
            val value = if (!scrubKeys.contains(key.toLowerCase()) || it.value.toString().isEmpty()) "${it.value}" else "*****"
            map[key] = value
        }

        return map
    }