package com.example.flowstateflowpoc.mvvm.domain

import com.example.flowstateflowpoc.mvvm.domain.mapper.toMVVMPhoto
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MVVMPhotoUseCase(
    private val repository: MVVMPhotoContract.Repository
) : MVVMPhotoContract.UseCase {
    override fun takePhotos(): Flow<List<Photo>> {
        return repository.findAll().map { response ->
            response.map { it.toMVVMPhoto() }
        }
    }
}