package com.sonasetiana.nextgames.presentation.main

import androidx.lifecycle.LiveData
import com.sonasetiana.core.data.base.BaseViewModelContract
import com.sonasetiana.core.data.base.SingleLiveEvent
import com.sonasetiana.core.domain.data.Game

interface MainViewModelContract : BaseViewModelContract {
    fun getGames()

    fun searchGame(keyword: String)

    fun getMoreGames()

    fun successGetGames(): LiveData<List<Game>>

    fun errorGetGames(): SingleLiveEvent<String>

    fun loadingSearchGame(): LiveData<Boolean>

    fun successSearchGame(): LiveData<List<Game>>

    fun errorSearchGame(): SingleLiveEvent<String>

    fun loadingGetMoreGames(): LiveData<Boolean>

    fun successGetMoreGames(): LiveData<List<Game>>
}