package com.sonasetiana.core.domain.data

import com.sonasetiana.core.data.local.entity.FavoriteEntity
import com.sonasetiana.core.data.remote.models.GameDetailModel
import com.sonasetiana.core.data.remote.models.GameModel

object DataMapper {
    fun mappingEntityToFavorite(item: FavoriteEntity) = Favorite(
        gameId = item.gameId,
        name = item.name,
        slug = item.slug,
        rating = item.rating,
        released = item.released,
        image = item.image,
    )

    fun mappingFavoriteToEntity(item: Favorite) = FavoriteEntity(
        gameId = item.gameId,
        name = item.name,
        slug = item.slug,
        rating = item.rating,
        released = item.released,
        image = item.image,
    )

    fun mappingGameDetailToFavorite(item: GameDetail) = Favorite(
        gameId = item.id,
        name = item.name,
        slug = item.slug,
        rating = item.rating,
        released = item.released,
        image = item.image,
    )

    fun mappingGameData(item: GameModel) = Game(
        id = item.id,
        name = item.name,
        slug = item.slug,
        rating = item.rating,
        released = item.released,
        image = item.backgroundImage,
    )

    fun mappingGameDetailData(item: GameDetailModel) = GameDetail(
        id = item.id,
        name = item.name,
        nameOriginal = item.nameOriginal,
        image = item.backgroundImage,
        description = item.description,
        descriptionRaw = item.descriptionRaw,
        website = item.website,
        released = item.released,
        rating = item.rating,
        slug = item.slug,
        platforms = item.platforms.map { it.platform.name },
        stores = item.stores.map { it.store.name },
        developers = item.developers.map { it.name },
        publishers = item.publishers.map { it.name },
        tags = item.tags.map { it.name },
        genres = item.genres.map { it.name },
    )
}