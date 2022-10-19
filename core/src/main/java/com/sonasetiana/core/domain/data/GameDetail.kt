package com.sonasetiana.core.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameDetail(
    var id: Int,
    var name: String,
    val nameOriginal: String,
    var image: String,
    var description: String,
    var descriptionRaw: String,
    var website: String,
    var released: String,
    var rating: Double,
    var slug: String,
    var platforms: List<String>,
    var stores: List<String>,
    var developers: List<String>,
    var publishers: List<String>,
    var tags: List<String>,
    var genres: List<String>,
) : Parcelable
