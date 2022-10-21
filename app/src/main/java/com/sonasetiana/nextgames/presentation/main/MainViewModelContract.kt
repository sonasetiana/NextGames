package com.sonasetiana.nextgames.presentation.main

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.sonasetiana.core.data.base.BaseViewModelContract
import com.sonasetiana.core.domain.data.Game

interface MainViewModelContract : BaseViewModelContract {
    fun getGames() : LiveData<PagingData<Game>>

    fun searchGame(keyword: String) : LiveData<PagingData<Game>>
}