package com.sonasetiana.core.data.remote.models

import com.google.gson.annotations.SerializedName

data class RatingModel(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("count")
    val count: Int,
    @field:SerializedName("percent")
    val percent: Double
)
