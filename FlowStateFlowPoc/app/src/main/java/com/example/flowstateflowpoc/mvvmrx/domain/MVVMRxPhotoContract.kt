package com.example.flowstateflowpoc.mvvmrx.domain

import com.example.flowstateflowpoc.network.models.PhotoResponse
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import io.reactivex.rxjava3.core.Single

interface MVVMRxPhotoContract {
    interface UseCase {
        fun getPhotos(): Single<List<Photo>>
    }

    interface Repository {
        fun getPhotos(): Single<List<PhotoResponse>>
    }
}