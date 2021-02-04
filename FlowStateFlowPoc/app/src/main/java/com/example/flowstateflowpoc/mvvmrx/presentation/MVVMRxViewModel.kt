package com.example.flowstateflowpoc.mvvmrx.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flowstateflowpoc.mvvmrx.domain.MVVMRxPhotoContract
import com.example.flowstateflowpoc.mvvmrx.domain.MVVMRxPhotoUseCase
import com.example.flowstateflowpoc.shared.Result
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MVVMRxViewModel(
    private val useCase: MVVMRxPhotoContract.UseCase = MVVMRxPhotoUseCase(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableLiveData<Result<List<Photo>>>(Result.Empty)
    val state: LiveData<Result<List<Photo>>>
        get() = _state

    fun takePhotos() {
        compositeDisposable += useCase.getPhotos()
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .doOnSubscribe {
                _state.value = Result.Loading
            }
            .doAfterTerminate {
                _state.value = Result.DismissLoading
            }
            .subscribeBy(
                onSuccess = {
                    _state.value = Result.Success(it)
                },
                onError = {
                    _state.value = Result.Error(it)
                },
            )
    }

    fun cancelTakePhotos() {
        _state.value = Result.DismissLoading
        _state.value = Result.Error(RuntimeException("Cancelled request"))
        compositeDisposable.dispose()
    }

    fun onWillDestroy() {
        compositeDisposable.dispose()
    }
}