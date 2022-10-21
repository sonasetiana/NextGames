package com.sonasetiana.core.data.remote.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.sonasetiana.core.data.remote.dataSource.RemoteDataSource
import com.sonasetiana.core.domain.data.DataMapper
import com.sonasetiana.core.domain.data.Game
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject

class SearchGamePagingDataSource(
    private val remote: RemoteDataSource,
    private val keyword: String
) : RxPagingSource<Int, Game>(){
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Game>> {
        val result = SingleSubject.create<LoadResult<Int, Game>>()
        val page = params.key ?: INITIAL_PAGE_INDEX

        remote.searchGames(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .map { it.results.map { i -> DataMapper.mappingGameData(i) } }
            .subscribe({
                result.onSuccess(
                    LoadResult.Page(
                        data = it,
                        prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                        nextKey = if (it.isEmpty()) null else page + 1
                    )
                )

            }, { err ->
                result.onSuccess(LoadResult.Error(err))
            })
        return result
    }

}