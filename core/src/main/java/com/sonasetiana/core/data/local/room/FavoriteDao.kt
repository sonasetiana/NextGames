package com.sonasetiana.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sonasetiana.core.data.local.entity.FavoriteEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorite(): Flowable<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite where gameId = :id")
    fun checkFavorite(id: Int): Flowable<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity): Completable

    @Query("DELETE FROM favorite WHERE gameId = :id")
    fun delete(id: Int): Single<Int>

}