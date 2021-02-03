package com.example.flowstateflowpoc.mvp.data

import com.example.flowstateflowpoc.mvp.domain.MVPPhotoContract
import com.example.flowstateflowpoc.network.PhotoService
import com.example.flowstateflowpoc.network.models.PhotoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MVPPhotoRepository(
    private val service: PhotoService
) : MVPPhotoContract.Repository {
    override fun findAll(): Flow<List<PhotoResponse>> {
        return flow {
            emit(service.getPhotos())
        }
    }
}