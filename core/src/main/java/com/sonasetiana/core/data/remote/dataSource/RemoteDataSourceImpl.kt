package com.sonasetiana.core.data.remote.dataSource

import com.sonasetiana.core.data.remote.models.GameDetailModel
import com.sonasetiana.core.data.remote.models.GameResponse
import com.sonasetiana.core.data.remote.services.ApiService
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val service: ApiService
): RemoteDataSource{
    override fun getAllGames(limit: Int, page: Int): Single<GameResponse> = service.getAllGames(limit = limit, page = page)

    override fun searchGames(keyword: String): Single<GameResponse> = service.searchGames(keyword = keyword)

    override fun getDetailGame(id: Int): Flowable<GameDetailModel> = service.getDetailGame(id = id)

}