package com.sonasetiana.core.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite")
data class FavoriteEntity(

    @PrimaryKey
    @ColumnInfo(name = "gameId")
    var gameId: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "slug")
    var slug: String,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "image")
    var image: String,
) : Parcelable
