package com.sonasetiana.favorite.di

import com.sonasetiana.core.di.DynamicFeatureDependencies
import com.sonasetiana.favorite.presentation.FavoriteActivity
import dagger.Component

@Component(dependencies = [DynamicFeatureDependencies::class])
interface DfmDaggerComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Factory
    interface Factory {
        fun create(dependencies: DynamicFeatureDependencies): DfmDaggerComponent
    }
}