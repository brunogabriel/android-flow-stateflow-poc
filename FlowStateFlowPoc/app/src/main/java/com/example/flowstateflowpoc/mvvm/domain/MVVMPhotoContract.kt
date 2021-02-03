package com.example.flowstateflowpoc.mvvm.domain

import com.example.flowstateflowpoc.network.models.PhotoResponse
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import kotlinx.coroutines.flow.Flow

interface MVVMPhotoContract {
    interface UseCase {
        fun takePhotos(): Flow<List<Photo>>
    }

    interface Repository {
        fun findAll(): Flow<List<PhotoResponse>>
    }
}