package com.sonasetiana.core.data.remote.models

import com.google.gson.annotations.SerializedName

data class ScreenshotModel(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("image")
    val image: String
)
