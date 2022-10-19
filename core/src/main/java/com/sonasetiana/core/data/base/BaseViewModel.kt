package com.sonasetiana.core.data.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel(), BaseViewModelContract {
    private val compositeDisposable = CompositeDisposable()
    private val isLoading = MutableLiveData<Boolean>()

    override fun getIsLoading() = isLoading

    override fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}