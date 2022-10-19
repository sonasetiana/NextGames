package com.sonasetiana.favorite.presentation

import androidx.lifecycle.LiveData
import com.sonasetiana.core.data.base.BaseViewModelContract
import com.sonasetiana.core.data.base.SingleLiveEvent
import com.sonasetiana.core.domain.data.Favorite

interface FavoriteViewModelContact : BaseViewModelContract {
    fun getFavorites()

    fun deleteFromFavorite(id: Int)

    fun successGetFavorites(): LiveData<List<Favorite>>

    fun errorGetFavorites(): SingleLiveEvent<String>

    fun resultDeleteFavorite(): LiveData<String>
}