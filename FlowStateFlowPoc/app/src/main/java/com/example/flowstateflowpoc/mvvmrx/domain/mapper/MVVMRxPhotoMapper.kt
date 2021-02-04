package com.example.flowstateflowpoc.mvvmrx.domain.mapper

import com.example.flowstateflowpoc.network.models.PhotoResponse
import com.example.flowstateflowpoc.shared.adapter.models.Photo

fun PhotoResponse.toMVVMRxPhoto() = Photo(
    title,
    thumbnailUrl
)