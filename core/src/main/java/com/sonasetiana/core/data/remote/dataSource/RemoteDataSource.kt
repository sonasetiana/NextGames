package com.sonasetiana.core.data.remote.dataSource

import com.sonasetiana.core.data.remote.models.GameDetailModel
import com.sonasetiana.core.data.remote.models.GameResponse
import io.reactivex.Flowable
import io.reactivex.Single

interface RemoteDataSource {
    fun getAllGames(limit: Int, page: Int) : Single<GameResponse>
    fun searchGames(keyword: String) : Single<GameResponse>
    fun getDetailGame(id: Int) : Flowable<GameDetailModel>
}