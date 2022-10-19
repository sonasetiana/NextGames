package com.sonasetiana.nextgames.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sonasetiana.core.data.base.BaseViewModel
import com.sonasetiana.core.data.base.SingleLiveEvent
import com.sonasetiana.core.data.model.Results
import com.sonasetiana.core.domain.data.Favorite
import com.sonasetiana.core.domain.data.GameDetail
import com.sonasetiana.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: GameUseCase
) : BaseViewModel(), DetailViewModelContract {

    private val successGeDetail = MutableLiveData<GameDetail>()

    private val errorGetDetail = SingleLiveEvent<String>()

    private val resultSaveFavorite = MutableLiveData<String>()

    private val resultDeleteFavorite = MutableLiveData<String>()

    private val successCheckFavorite = MutableLiveData<List<Favorite>>()

    private val errorCheckFavorite = SingleLiveEvent<String>()

    override fun getDetail(id: Int) {
        val disposable = useCase.getDetailGame(id)
            .doOnSubscribe { setIsLoading(true) }
            .subscribe {
                setIsLoading(false)
                when(it) {
                    is Results.Success -> {
                        val data = it.data
                        successGeDetail.value = data
                    }
                    is Results.Error -> errorGetDetail.value = it.message
                }
            }
        addDisposable(disposable)
    }

    override fun saveToFavorite(favorite: Favorite) {
        val disposable = useCase.insertFavorite(favorite)
            .subscribe {
                resultSaveFavorite.value = if (it is Results.Success) it.data else  (it as Results.Error).message
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

    override fun checkIsFavorite(id: Int) {
        val disposable = useCase.checkFavorite(id)
            .subscribe {
                when(it) {
                    is Results.Success -> {
                        val data = it.data
                        successCheckFavorite.value = data
                    }
                    is Results.Error -> errorCheckFavorite.value = it.message
                }
            }
        addDisposable(disposable)
    }

    override fun successGetDetail(): LiveData<GameDetail> = successGeDetail

    override fun errorGetDetail(): SingleLiveEvent<String> = errorGetDetail

    override fun resultSaveFavorite(): LiveData<String> = resultSaveFavorite

    override fun resultDeleteFavorite(): LiveData<String> = resultDeleteFavorite

    override fun successCheckFavorite(): LiveData<List<Favorite>> = successCheckFavorite

    override fun errorCheckFavorite(): SingleLiveEvent<String> = errorCheckFavorite
}