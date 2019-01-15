package com.theblissprogrammer.boubyanbank.businesslogic.extensions

import androidx.room.TypeConverter
import com.theblissprogrammer.boubyanbank.businesslogic.stores.users.models.Geo

/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
class Converters {
    @TypeConverter
    fun geofromString(value: String?): Geo? {
        return if (value != null) Geo(value) else null
    }

    @TypeConverter
    fun geoToString(geo: Geo?): String? {
        return geo?.toString()
    }
}