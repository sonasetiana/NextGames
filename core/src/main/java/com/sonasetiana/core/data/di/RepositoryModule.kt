package com.sonasetiana.core.data.di

import com.sonasetiana.core.data.local.dataSource.LocalDataSource
import com.sonasetiana.core.data.local.dataSource.LocalDataSourceImpl
import com.sonasetiana.core.data.remote.dataSource.RemoteDataSource
import com.sonasetiana.core.data.remote.dataSource.RemoteDataSourceImpl
import com.sonasetiana.core.data.repository.GameRepositoryImpl
import com.sonasetiana.core.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [LocalModule::class, RemoteModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
 @Binds
 abstract fun provideLocalDataSource(dataSource: LocalDataSourceImpl): LocalDataSource
 @Binds
 abstract fun provideRemoteDataSource(dataSource: RemoteDataSourceImpl): RemoteDataSource
 @Binds
 abstract fun provideRepository(repository: GameRepositoryImpl): GameRepository
}