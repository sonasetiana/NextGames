package com.sonasetiana.favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sonasetiana.core.data.base.BaseViewModel
import com.sonasetiana.core.data.base.SingleLiveEvent
import com.sonasetiana.core.data.model.Results
import com.sonasetiana.core.domain.data.Favorite
import com.sonasetiana.core.domain.usecase.GameUseCase

class FavoriteViewModel(
    private val useCase: GameUseCase
): BaseViewModel(), FavoriteViewModelContact {

    private val successGetFavorites = MutableLiveData<List<Favorite>>()

    private val errorGetFavorites = SingleLiveEvent<String>()

    private val resultDeleteFavorite = MutableLiveData<String>()

    override fun getFavorites() {
        val disposable = useCase.getAllFavorite()
            .doOnSubscribe { setIsLoading(true) }
            .subscribe {
                setIsLoading(false)
                when (it) {
                    is Results.Success -> {
                        val items = it.data
                        successGetFavorites.value = items
                    }
                    is Results.Error -> errorGetFavorites.value = it.message
                }
            }
        addDisposable(disposable)
    }

    override fun deleteFromFavorite(id: Int) {
        val disposable = useCase.deleteFavorite(id)
            .subscribe {
                resultDeleteFavorite.value = if (it is Results.Success) it.data else  (it as Results.Error).message
            }
        addDisposable(disposable)
    }

    override fun successGetFavorites(): LiveData<List<Favorite>> = successGetFavorites

    override fun errorGetFavorites(): SingleLiveEvent<String> = errorGetFavorites

    override fun resultDeleteFavorite(): LiveData<String> = resultDeleteFavorite


}