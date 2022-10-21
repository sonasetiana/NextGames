package com.sonasetiana.core.data.remote.dataSource

import com.sonasetiana.core.data.remote.models.GameDetailModel
import com.sonasetiana.core.data.remote.models.GameResponse
import io.reactivex.Flowable

interface RemoteDataSource {
    fun getAllGames(limit: Int, page: Int) : Flowable<GameResponse>
    fun searchGames(keyword: String) : Flowable<GameResponse>
    fun getDetailGame(id: Int) : Flowable<GameDetailModel>
}