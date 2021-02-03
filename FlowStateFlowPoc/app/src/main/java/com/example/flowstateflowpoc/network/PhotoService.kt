package com.example.flowstateflowpoc.network

import com.example.flowstateflowpoc.network.models.PhotoResponse
import retrofit2.http.GET

interface PhotoService {
    @GET("/photos")
    suspend fun getPhotos(): List<PhotoResponse>
}