package com.sonasetiana.nextgames.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.paging.PagingData
import com.sonasetiana.core.data.base.BaseViewModel
import com.sonasetiana.core.domain.data.Game
import com.sonasetiana.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GameUseCase
) : BaseViewModel(), MainViewModelContract{
    override fun getGames(): LiveData<PagingData<Game>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.getAllGames())
    }

    override fun searchGame(keyword: String): LiveData<PagingData<Game>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.searchGames(keyword))
    }

}