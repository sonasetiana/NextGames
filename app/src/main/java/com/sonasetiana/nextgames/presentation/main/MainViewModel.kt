package com.sonasetiana.nextgames.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sonasetiana.core.data.base.BaseViewModel
import com.sonasetiana.core.data.base.SingleLiveEvent
import com.sonasetiana.core.data.model.Results
import com.sonasetiana.core.domain.data.Game
import com.sonasetiana.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GameUseCase
) : BaseViewModel(), MainViewModelContract{
    private var page: Int = 1
    private val compositeDisposable = CompositeDisposable()

    private val loadingSearchGame = MutableLiveData<Boolean>()
    private val loadingGetMoreGames = MutableLiveData<Boolean>()
    private val successGetGames = MutableLiveData<List<Game>>()
    private val successSearchGame = MutableLiveData<List<Game>>()
    private val successGetMoreGames = MutableLiveData<List<Game>>()
    private val errorGetGames = SingleLiveEvent<String>()
    private val errorSearchGame = SingleLiveEvent<String>()

    override fun getGames() {
        page = 1
        val disposable = useCase.getAllGames(LIMIT, page)
            .doOnSubscribe { setIsLoading(true) }
            .subscribe {
                setIsLoading(false)
                when (it) {
                    is Results.Success -> {
                        val items = it.data
                        successGetGames.value = items
                        if (items.isNotEmpty()) {
                            page++
                        }
                    }
                    is Results.Error -> errorGetGames.value = it.message
                }
            }

        addDisposable(disposable)
    }

    override fun searchGame(keyword: String) {
        val disposable = useCase.searchGames(keyword)
            .doOnSubscribe { loadingSearchGame.value = true }
            .subscribe {
                loadingSearchGame.value = false
                when (it) {
                    is Results.Success -> {
                        val items = it.data
                        successSearchGame.value = items
                    }
                    is Results.Error -> errorSearchGame.value = it.message
                }
            }
        compositeDisposable.add(disposable)
    }

    override fun getMoreGames() {
        val disposable = useCase.getAllGames(LIMIT, page)
            .doOnSubscribe { loadingGetMoreGames.value = true }
            .subscribe {
                loadingGetMoreGames.value = false
                if (it is Results.Success) {
                    val items = it.data
                    if (items.isNotEmpty()) {
                        successGetMoreGames.value = items
                        page++
                    }
                }
            }

        addDisposable(disposable)
    }

    override fun successGetGames(): LiveData<List<Game>> = successGetGames

    override fun errorGetGames(): SingleLiveEvent<String> = errorGetGames

    override fun loadingGetMoreGames(): LiveData<Boolean> = loadingGetMoreGames

    override fun successGetMoreGames(): LiveData<List<Game>> = successGetMoreGames

    override fun loadingSearchGame(): LiveData<Boolean> = loadingSearchGame

    override fun successSearchGame(): LiveData<List<Game>> = successSearchGame

    override fun errorSearchGame(): SingleLiveEvent<String> = errorSearchGame

    companion object {
        private const val LIMIT = 10
    }
}