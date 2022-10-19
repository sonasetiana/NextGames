package com.sonasetiana.core.data.remote.models

import com.google.gson.annotations.SerializedName

data class TagModel(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("slug")
    val slug: String,
    @field:SerializedName("language")
    val language: String,
    @field:SerializedName("games_count")
    val gamesCount: Int,
    @field:SerializedName("image_background")
    val imageBackground: String
)
