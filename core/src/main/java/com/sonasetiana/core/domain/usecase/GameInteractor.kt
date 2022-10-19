package com.sonasetiana.core.domain.usecase

import com.sonasetiana.core.data.model.Results
import com.sonasetiana.core.domain.data.DataMapper
import com.sonasetiana.core.domain.data.Favorite
import com.sonasetiana.core.domain.data.Game
import com.sonasetiana.core.domain.data.GameDetail
import com.sonasetiana.core.domain.repository.GameRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GameInteractor @Inject constructor(
    private val repository: GameRepository
): GameUseCase{
    override fun getAllGames(limit: Int, page: Int): Flowable<Results<List<Game>>> = repository.getAllGames(limit, page)

    override fun searchGames(keyword: String): Flowable<Results<List<Game>>> = repository.searchGames(keyword)

    override fun getDetailGame(id: Int): Flowable<Results<GameDetail>> = repository.getDetailGame(id)

    override fun getAllFavorite(): Flowable<Results<List<Favorite>>> = repository.getAllFavorite()

    override fun insertFavorite(favorite: Favorite): Flowable<Results<String>> = repository.insertFavorite(DataMapper.mappingFavoriteToEntity(favorite))

    override fun checkFavorite(id: Int): Flowable<Results<List<Favorite>>> = repository.checkFavorite(id)

    override fun deleteFavorite(id: Int): Flowable<Results<String>> = repository.deleteFavorite(id)
}