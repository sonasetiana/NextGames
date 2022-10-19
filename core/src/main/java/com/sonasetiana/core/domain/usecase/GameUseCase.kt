package com.sonasetiana.core.domain.usecase

import com.sonasetiana.core.data.model.Results
import com.sonasetiana.core.domain.data.Favorite
import com.sonasetiana.core.domain.data.Game
import com.sonasetiana.core.domain.data.GameDetail
import io.reactivex.Flowable

interface GameUseCase {
    fun getAllGames(limit: Int, page: Int) : Flowable<Results<List<Game>>>
    fun searchGames(keyword: String) : Flowable<Results<List<Game>>>
    fun getDetailGame(id: Int) : Flowable<Results<GameDetail>>
    fun getAllFavorite(): Flowable<Results<List<Favorite>>>
    fun insertFavorite(favorite: Favorite): Flowable<Results<String>>
    fun checkFavorite(id: Int): Flowable<Results<List<Favorite>>>
    fun deleteFavorite(id: Int): Flowable<Results<String>>
}