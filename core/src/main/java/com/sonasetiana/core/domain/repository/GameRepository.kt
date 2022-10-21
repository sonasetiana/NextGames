package com.sonasetiana.core.domain.repository

import androidx.paging.PagingData
import com.sonasetiana.core.data.local.entity.FavoriteEntity
import com.sonasetiana.core.data.model.Results
import com.sonasetiana.core.domain.data.Favorite
import com.sonasetiana.core.domain.data.Game
import com.sonasetiana.core.domain.data.GameDetail
import io.reactivex.Flowable

interface GameRepository {
    fun getAllGames() : Flowable<PagingData<Game>>
    fun searchGames(keyword: String) : Flowable<PagingData<Game>>
    fun getDetailGame(id: Int) : Flowable<Results<GameDetail>>
    fun getAllFavorite(): Flowable<Results<List<Favorite>>>
    fun insertFavorite(favorite: FavoriteEntity): Flowable<Results<String>>
    fun checkFavorite(id: Int): Flowable<Results<List<Favorite>>>
    fun deleteFavorite(id: Int): Flowable<Results<String>>
}