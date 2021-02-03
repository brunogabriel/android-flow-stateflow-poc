package com.example.flowstateflowpoc.mvvm.domain.mapper

import com.example.flowstateflowpoc.network.models.PhotoResponse
import com.example.flowstateflowpoc.shared.adapter.models.Photo

fun PhotoResponse.toMVVMPhoto() = Photo(
    title,
    thumbnailUrl
)