package com.sonasetiana.core.data.remote.services

import com.sonasetiana.core.BuildConfig
import com.sonasetiana.core.data.remote.models.GameDetailModel
import com.sonasetiana.core.data.remote.models.GameResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    fun getAllGames(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("page_size") limit: Int = 10,
        @Query("page") page: Int = 1,
    ) : Single<GameResponse>

    @GET("games")
    fun searchGames(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("search") keyword: String
    ) : Single<GameResponse>

    @GET("games/{id}")
    fun getDetailGame(
        @Path("id") id: Int,
        @Query("key") key: String = BuildConfig.API_KEY
    ) : Flowable<GameDetailModel>
}