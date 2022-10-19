package com.sonasetiana.core.data.base

import androidx.lifecycle.LiveData
import io.reactivex.disposables.Disposable

interface BaseViewModelContract {
    fun getIsLoading(): LiveData<Boolean>

    fun setIsLoading(isLoading: Boolean)

    fun addDisposable(disposable: Disposable)
}