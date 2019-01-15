package com.theblissprogrammer.boubyanbank.businesslogic.enums

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

enum class SecurityProperty(val id: String) {
    TOKEN("KEY_TOKEN"),
    EMAIL("KEY_EMAIL"),
    PASSWORD("KEY_PASSWORD"),
    JWT("KEY_JWT")
}