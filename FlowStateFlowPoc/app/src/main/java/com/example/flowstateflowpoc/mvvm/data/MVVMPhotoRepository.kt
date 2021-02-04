package com.example.flowstateflowpoc.mvvm.data

import com.example.flowstateflowpoc.mvvm.domain.MVVMPhotoContract
import com.example.flowstateflowpoc.network.PhotoService
import com.example.flowstateflowpoc.network.RetrofitApp
import com.example.flowstateflowpoc.network.models.PhotoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MVVMPhotoRepository(
    private val service: PhotoService = RetrofitApp.provideService(PhotoService::class.java)
) : MVVMPhotoContract.Repository {
    override fun findAll(): Flow<List<PhotoResponse>> {
        return flow {
            emit(service.getPhotos())
        }
    }
}