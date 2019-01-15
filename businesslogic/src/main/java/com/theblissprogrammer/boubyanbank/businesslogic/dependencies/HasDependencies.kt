package com.theblissprogrammer.boubyanbank.businesslogic.dependencies


/**
 * Created by ahmedsaad on 2019-01-14.
 * Copyright © 2019. All rights reserved.
 */
interface HasDependencies {
    /// Container for dependency instance factories
    val dependencies: AppDependable
        get() {
            return DependencyInjector.dependencies
        }
}

/// Used to pass around dependency container
/// which can be reassigned to another container
internal class DependencyInjector {
    companion object {
        lateinit var dependencies: AppDependable
    }
}