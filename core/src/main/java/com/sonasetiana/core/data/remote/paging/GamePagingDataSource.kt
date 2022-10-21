package com.sonasetiana.core.data.remote.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.sonasetiana.core.data.remote.dataSource.RemoteDataSource
import com.sonasetiana.core.domain.data.DataMapper
import com.sonasetiana.core.domain.data.Game
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GamePagingDataSource(
    private val remote: RemoteDataSource
) : RxPagingSource<Int, Game>(){
    private companion object {
        const val LIMIT = 10
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Game>> {
        val page = params.key ?: INITIAL_PAGE_INDEX
        return remote.getAllGames(LIMIT, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data.results.map { DataMapper.mappingGameData(it)} }
            .map {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            }
    }
}