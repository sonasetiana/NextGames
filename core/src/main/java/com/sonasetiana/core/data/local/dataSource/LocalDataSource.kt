package com.sonasetiana.core.data.local.dataSource

import com.sonasetiana.core.data.local.entity.FavoriteEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface LocalDataSource {
    fun getAllFavorite(): Flowable<List<FavoriteEntity>>
    fun insertFavorite(favorite: FavoriteEntity): Completable
    fun checkFavorite(id: Int): Flowable<List<FavoriteEntity>>
    fun deleteFavorite(id: Int): Single<Int>
}