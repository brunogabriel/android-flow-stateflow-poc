package com.example.flowstateflowpoc.mvvmrx.domain

import com.example.flowstateflowpoc.mvvmrx.domain.mapper.toMVVMRxPhoto
import com.example.flowstateflowpoc.network.models.PhotoResponse
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class MVVMRxPhotoUseCaseTest {
    @MockK
    private lateinit var repository: MVVMRxPhotoContract.Repository
    private lateinit var useCase: MVVMRxPhotoContract.UseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic("com.example.flowstateflowpoc.mvvmrx.domain.mapper.MVVMRxPhotoMapperKt")
        useCase = MVVMRxPhotoUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkStatic("com.example.flowstateflowpoc.mvvmrx.domain.mapper.MVVMRxPhotoMapperKt")
    }

    @Test
    fun `should get photos`() {
        // given
        val models = listOf<PhotoResponse>(mockk())
        val photos = listOf<Photo>(mockk())

        models.forEachIndexed { index, photoResponse ->
            every { photoResponse.toMVVMRxPhoto() } returns photos[index]
        }

        every { repository.getPhotos() } returns Single.just(models)

        // then
        useCase.getPhotos()
            .test()
            .assertValue(photos)
            .assertComplete()
    }
}