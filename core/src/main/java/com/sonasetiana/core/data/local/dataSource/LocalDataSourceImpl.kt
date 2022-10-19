package com.sonasetiana.core.data.local.dataSource

import com.sonasetiana.core.data.local.entity.FavoriteEntity
import com.sonasetiana.core.data.local.room.FavoriteDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val dao: FavoriteDao
) : LocalDataSource{

    override fun getAllFavorite(): Flowable<List<FavoriteEntity>> = dao.getAllFavorite()

    override fun insertFavorite(favorite: FavoriteEntity): Completable = dao.insertFavorite(favorite)

    override fun checkFavorite(id: Int): Flowable<List<FavoriteEntity>> = dao.checkFavorite(id)

    override fun deleteFavorite(id: Int): Single<Int> = dao.delete(id)

}