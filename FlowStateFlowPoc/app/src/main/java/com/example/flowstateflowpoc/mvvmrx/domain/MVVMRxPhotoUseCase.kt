package com.example.flowstateflowpoc.mvvmrx.domain

import com.example.flowstateflowpoc.mvvmrx.domain.mapper.toMVVMRxPhoto
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import io.reactivex.rxjava3.core.Single

class MVVMRxPhotoUseCase(
    private val repository: MVVMRxPhotoContract.Repository
) : MVVMRxPhotoContract.UseCase {
    override fun getPhotos(): Single<List<Photo>> {
        return repository.getPhotos().map { models ->
            models.map { it.toMVVMRxPhoto() }
        }
    }
}