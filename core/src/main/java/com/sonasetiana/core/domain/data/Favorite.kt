package com.sonasetiana.core.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    var gameId: Int,
    var name: String,
    var slug: String,
    var rating: Double,
    var released: String,
    var image: String,
) : Parcelable
