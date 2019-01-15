package com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */

data class Address(
    val street: String? = null,
    val suite: String? = null,
    val city: String? = null,
    val zipcode: String? = null,
    val geo:  Geo? = null
)

data class Geo (
    var lat: Double? = null,
    var lng: Double? = null) {

    constructor(string: String): this() {
        val values = string.split(",")
        this.lat = values[0].toDoubleOrNull()
        this.lng = values[1].toDoubleOrNull()
    }

    override fun toString(): String {
        return "$lat,$lng"
    }
}