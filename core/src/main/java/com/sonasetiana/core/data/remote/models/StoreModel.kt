package com.sonasetiana.core.data.remote.models

import com.google.gson.annotations.SerializedName

data class StoreModel(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("slug")
    val slug: String
)

data class StoreResponse(
    @field:SerializedName("store")
    val store: StoreModel
)
