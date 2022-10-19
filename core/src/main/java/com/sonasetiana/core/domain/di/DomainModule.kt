package com.sonasetiana.core.domain.di

import com.sonasetiana.core.domain.usecase.GameInteractor
import com.sonasetiana.core.domain.usecase.GameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {
    @Binds
    @ViewModelScoped
    abstract fun provideUseCase(interactor: GameInteractor): GameUseCase
}