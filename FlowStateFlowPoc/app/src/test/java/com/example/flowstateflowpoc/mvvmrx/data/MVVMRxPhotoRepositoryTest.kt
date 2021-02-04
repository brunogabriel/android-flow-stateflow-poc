package com.example.flowstateflowpoc.mvvmrx.data

import com.example.flowstateflowpoc.mvvm.data.MVVMPhotoRepository
import com.example.flowstateflowpoc.mvvmrx.domain.MVVMRxPhotoContract
import com.example.flowstateflowpoc.network.PhotoService
import com.example.flowstateflowpoc.network.models.PhotoResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class MVVMRxPhotoRepositoryTest {
    @MockK
    private lateinit var service: PhotoService
    private lateinit var repository: MVVMRxPhotoContract.Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = MVVMRxPhotoRepository(service)
    }

    @Test
    fun `should take photos`() {
        // given
        val models = listOf<PhotoResponse>(mockk())
        every { service.getPhotosRx() } returns Single.just(models)

        // then
        repository.getPhotos()
            .test()
            .assertValue(models)
            .assertComplete()
    }
}