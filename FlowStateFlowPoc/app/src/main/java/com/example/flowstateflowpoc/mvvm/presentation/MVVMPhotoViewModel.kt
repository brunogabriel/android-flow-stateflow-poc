package com.example.flowstateflowpoc.mvvm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowstateflowpoc.mvvm.domain.MVVMPhotoContract
import com.example.flowstateflowpoc.shared.Result
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MVVMPhotoViewModel(
        private val useCase: MVVMPhotoContract.UseCase,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var job: Job? = null

    private val _state = MutableStateFlow<Result<List<Photo>>>(Result.Empty)
    val state: StateFlow<Result<List<Photo>>>
        get() = _state

    fun takePhotos() {
        job = useCase.takePhotos()
                .flowOn(ioDispatcher)
                .onEach { _state.value = Result.Success(data = it) }
                .onStart { _state.value = Result.Loading }
                .onCompletion {
                    _state.value = Result.DismissLoading
                    if (it is CancellationException) {
                        _state.value = Result.Error(it)
                    }
                }
                .catch { _state.value = Result.Error(it) }
                .launchIn(viewModelScope)
    }

    fun cancelTakePhotos() = job?.cancel()
}