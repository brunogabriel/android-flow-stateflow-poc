package com.example.flowstateflowpoc.mvp.presentation

import com.example.flowstateflowpoc.mvp.domain.MVPPhotoContract
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MVPPhotoPresenter(
    private val view: MVPPhotoContract.View,
    private val useCase: MVPPhotoContract.UseCase,
    private val coroutineScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MVPPhotoContract.Presenter {

    private var job: Job? = null

    override fun takePhotos() {
        job = useCase.takePhotos()
            .flowOn(ioDispatcher)
            .onEach { view.showPhotos(it) }
            .onStart { view.showLoading() }
            .onCompletion {
                view.dismissLoading()
                if (it is CancellationException) {
                    view.showCancellation()
                }
            }
            .catch { view.showError() }
            .launchIn(coroutineScope)
    }

    override fun cancelTakePhotos() {
        job?.cancel()
    }

    override fun willDestroy() {
        coroutineScope.cancel()
    }
}