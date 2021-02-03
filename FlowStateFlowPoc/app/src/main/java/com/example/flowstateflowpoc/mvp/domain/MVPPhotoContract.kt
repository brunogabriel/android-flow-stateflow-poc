package com.example.flowstateflowpoc.mvp.domain

import com.example.flowstateflowpoc.network.models.PhotoResponse
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import kotlinx.coroutines.flow.Flow

interface MVPPhotoContract {
    interface View {
        fun showLoading()
        fun dismissLoading()
        fun showError()
        fun showCancellation()
        fun showPhotos(newPhotos: List<Photo>)
    }

    interface Presenter {
        fun takePhotos()
        fun cancelTakePhotos()
        fun willDestroy()
    }

    interface UseCase {
        fun takePhotos(): Flow<List<Photo>>
    }

    interface Repository {
        fun findAll(): Flow<List<PhotoResponse>>
    }
}