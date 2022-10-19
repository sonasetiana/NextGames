package com.sonasetiana.core.data.remote.models

import com.google.gson.annotations.SerializedName

data class GameDetailModel(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("slug")
    val slug: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("name_original")
    val nameOriginal: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("description_raw")
    val descriptionRaw: String,
    @field:SerializedName("released")
    val released: String,
    @field:SerializedName("updated")
    val updated: String,
    @field:SerializedName("background_image")
    val backgroundImage: String,
    @field:SerializedName("background_image_additional")
    val backgroundImageAdditional: String,
    @field:SerializedName("website")
    val website: String,
    @field:SerializedName("rating")
    val rating: Double,
    @field:SerializedName("added")
    val added: Int,
    @field:SerializedName("playtime")
    val playtime: Int,
    @field:SerializedName("saturated_color")
    val saturatedColor: String,
    @field:SerializedName("dominant_color")
    val dominantColor: String,
    @field:SerializedName("reviews_count")
    val reviewsCount: Int,
    @field:SerializedName("metacritic")
    val metacritic: Int,
    @field:SerializedName("ratings")
    val ratings: List<RatingModel>,
    @field:SerializedName("platforms")
    val platforms: List<PlatformResponse>,
    @field:SerializedName("stores")
    val stores: List<StoreResponse>,
    @field:SerializedName("developers")
    val developers: List<DeveloperModel>,
    @field:SerializedName("publishers")
    val publishers: List<DeveloperModel>,
    @field:SerializedName("tags")
    val tags: List<TagModel>,
    @field:SerializedName("genres")
    val genres: List<GenreModel>
)
