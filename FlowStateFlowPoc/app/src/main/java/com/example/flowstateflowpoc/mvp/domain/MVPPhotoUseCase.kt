package com.example.flowstateflowpoc.mvp.domain

import com.example.flowstateflowpoc.mvp.domain.mapper.toMVPPhoto
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MVPPhotoUseCase(
    private val repository: MVPPhotoContract.Repository
) : MVPPhotoContract.UseCase {
    override fun takePhotos(): Flow<List<Photo>> = repository.findAll().map { response ->
        response.map { it.toMVPPhoto() }
    }
}