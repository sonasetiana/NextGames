package com.sonasetiana.core.data.remote.models

import com.google.gson.annotations.SerializedName

data class GameModel(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("slug")
    val slug: String,
    @field:SerializedName("playtime")
    val playtime: Int,
    @field:SerializedName("released")
    val released: String,
    @field:SerializedName("updated")
    val updated: String,
    @field:SerializedName("score")
    val score: String,
    @field:SerializedName("saturated_color")
    val saturatedColor: String,
    @field:SerializedName("dominant_color")
    val dominantColor: String,
    @field:SerializedName("background_image")
    val backgroundImage: String?,
    @field:SerializedName("rating")
    val rating: Double,
    @field:SerializedName("ratings_count")
    val ratingsCount: Int,
    @field:SerializedName("reviews_text_count")
    val reviewsTextCount: Int,
    @field:SerializedName("added")
    val added: Int,
    @field:SerializedName("metacritic")
    val metacritic: Int,
    @field:SerializedName("suggestions_count")
    val suggestionsCount: Int,
    @field:SerializedName("reviews_count")
    val reviewsCount: Int,
    @field:SerializedName("platforms")
    val platforms: List<PlatformResponse>,
    @field:SerializedName("stores")
    val stores: List<StoreResponse>,
    @field:SerializedName("ratings")
    val ratings: List<RatingModel>,
    @field:SerializedName("tags")
    val tags: List<TagModel>,
    @field:SerializedName("short_screenshots")
    val shortScreenshots: List<ScreenshotModel>,
    @field:SerializedName("genres")
    val genres: List<GenreModel>
)

data class GameResponse(
    @field:SerializedName("count")
    val count: Int,
    @field:SerializedName("next")
    val next: String?,
    @field:SerializedName("previous")
    val previous: String?,
    @field:SerializedName("results")
    val results: List<GameModel>,
)
