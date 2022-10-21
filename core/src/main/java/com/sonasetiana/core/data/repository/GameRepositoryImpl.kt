package com.sonasetiana.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.sonasetiana.core.data.local.dataSource.LocalDataSource
import com.sonasetiana.core.data.local.entity.FavoriteEntity
import com.sonasetiana.core.data.model.Results
import com.sonasetiana.core.data.remote.dataSource.RemoteDataSource
import com.sonasetiana.core.data.remote.models.toApiError
import com.sonasetiana.core.data.remote.paging.GamePagingDataSource
import com.sonasetiana.core.data.remote.paging.SearchGamePagingDataSource
import com.sonasetiana.core.domain.data.DataMapper
import com.sonasetiana.core.domain.data.Favorite
import com.sonasetiana.core.domain.data.Game
import com.sonasetiana.core.domain.data.GameDetail
import com.sonasetiana.core.domain.repository.GameRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
) : GameRepository {
    override fun getAllGames(): Flowable<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                GamePagingDataSource(remote)
            }
        ).flowable
    }

    override fun searchGames(keyword: String): Flowable<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SearchGamePagingDataSource(remote, keyword)
            }
        ).flowable
    }

    override fun getDetailGame(id: Int): Flowable<Results<GameDetail>> {
        val results = PublishSubject.create<Results<GameDetail>>()
        remote.getDetailGame(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({
                results.onNext(Results.Success(DataMapper.mappingGameDetailData(it)))
            },{
                val message = it.toApiError().message ?: it.message.toString()
                results.onNext(Results.Error(message))
            })
        return results.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getAllFavorite(): Flowable<Results<List<Favorite>>> {
        val results = PublishSubject.create<Results<List<Favorite>>>()
        local.getAllFavorite()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ items ->
                results.onNext(Results.Success(items.map { DataMapper.mappingEntityToFavorite(it)}))
            }, {
                results.onNext(Results.Error(it.message.toString()))
            })

        return results.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun insertFavorite(favorite: FavoriteEntity): Flowable<Results<String>> {
        val results = PublishSubject.create<Results<String>>()
        local.insertFavorite(favorite)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                results.onNext(Results.Success("${favorite.name} berhasil ditambahkan ke favorit"))
            }, {
                results.onNext(Results.Error(it.message.toString()))
            })

        return results.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun checkFavorite(id: Int): Flowable<Results<List<Favorite>>> {
        val results = PublishSubject.create<Results<List<Favorite>>>()
        local.checkFavorite(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ items ->
                results.onNext(Results.Success(items.map { DataMapper.mappingEntityToFavorite(it)}))
            }, {
                results.onNext(Results.Error(it.message.toString()))
            })

        return results.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun deleteFavorite(id: Int): Flowable<Results<String>> {
        val results = PublishSubject.create<Results<String>>()
        local.deleteFavorite(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                results.onNext(if(it > 0) Results.Success("Favorit barhasil dihapus") else Results.Error("Gagal hapus favorite"))
            }, {
                results.onNext(Results.Error(it.message.toString()))
            })

        return results.toFlowable(BackpressureStrategy.BUFFER)
    }


}