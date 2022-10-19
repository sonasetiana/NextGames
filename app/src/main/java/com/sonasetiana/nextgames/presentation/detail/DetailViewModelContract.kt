package com.sonasetiana.nextgames.presentation.detail

import androidx.lifecycle.LiveData
import com.sonasetiana.core.data.base.BaseViewModelContract
import com.sonasetiana.core.data.base.SingleLiveEvent
import com.sonasetiana.core.domain.data.Favorite
import com.sonasetiana.core.domain.data.GameDetail

interface DetailViewModelContract : BaseViewModelContract {
    fun getDetail(id: Int)

    fun saveToFavorite(favorite: Favorite)

    fun deleteFromFavorite(id: Int)

    fun checkIsFavorite(id: Int)

    fun successGetDetail(): LiveData<GameDetail>

    fun errorGetDetail(): SingleLiveEvent<String>

    fun resultSaveFavorite(): LiveData<String>

    fun resultDeleteFavorite(): LiveData<String>

    fun successCheckFavorite(): LiveData<List<Favorite>>

    fun errorCheckFavorite(): SingleLiveEvent<String>
}