package com.example.flowstateflowpoc.mvvmrx.data

import com.example.flowstateflowpoc.mvvmrx.domain.MVVMRxPhotoContract
import com.example.flowstateflowpoc.network.PhotoService
import com.example.flowstateflowpoc.network.RetrofitApp
import com.example.flowstateflowpoc.network.models.PhotoResponse
import io.reactivex.rxjava3.core.Single

class MVVMRxPhotoRepository(
    private val service: PhotoService = RetrofitApp.provideService(PhotoService::class.java)
) : MVVMRxPhotoContract.Repository {
    override fun getPhotos(): Single<List<PhotoResponse>> {
        return service.getPhotosRx()
    }
}