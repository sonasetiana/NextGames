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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) : FavoriteDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("nextgames".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room
            .databaseBuilder(context, FavoriteDatabase::class.java, "Favorite.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideFavoriteDao(db: FavoriteDatabase) : FavoriteDao = db.favoriteDao()

}