package com.sonasetiana.core.data.remote.models

import com.google.gson.annotations.SerializedName

data class PlatformModel(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("slug")
    val slug: String
)

data class PlatformResponse(
    @field:SerializedName("platform")
    val platform: PlatformModel
)
