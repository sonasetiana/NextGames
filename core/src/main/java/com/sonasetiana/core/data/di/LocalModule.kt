package com.sonasetiana.core.data.di

import android.content.Context
import androidx.room.Room
import com.sonasetiana.core.data.local.room.FavoriteDao
import com.sonasetiana.core.data.local.room.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) : FavoriteDatabase {
        return Room
            .databaseBuilder(context, FavoriteDatabase::class.java, "Favorite.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFavoriteDao(db: FavoriteDatabase) : FavoriteDao = db.favoriteDao()

}