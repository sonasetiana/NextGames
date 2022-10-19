package com.sonasetiana.core.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    var id: Int,
    var name: String,
    var image: String?,
    var released: String,
    var rating: Double,
    var slug: String,
) : Parcelable
