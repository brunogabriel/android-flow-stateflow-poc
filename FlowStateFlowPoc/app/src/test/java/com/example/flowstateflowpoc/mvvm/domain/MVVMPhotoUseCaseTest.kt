package com.example.flowstateflowpoc.mvvm.domain

import com.example.flowstateflowpoc.MainCoroutineTestRule
import com.example.flowstateflowpoc.mvvm.domain.mapper.toMVVMPhoto
import com.example.flowstateflowpoc.network.models.PhotoResponse
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MVVMPhotoUseCaseTest {
    @get:Rule
    var rule = MainCoroutineTestRule()

    @MockK
    private lateinit var repository: MVVMPhotoContract.Repository
    private lateinit var useCase: MVVMPhotoContract.UseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic("com.example.flowstateflowpoc.mvvm.domain.mapper.MVVMPhotoMapperKt")
        useCase = MVVMPhotoUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkStatic("com.example.flowstateflowpoc.mvvm.domain.mapper.MVVMPhotoMapperKt")
    }

    @Test
    fun `should take photos`() {
        // given
        val models = listOf<PhotoResponse>(mockk())
        val photos = listOf<Photo>(mockk())

        models.forEachIndexed { index, photoResponse ->
            every { photoResponse.toMVVMPhoto() } returns photos[index]
        }

        coEvery { repository.findAll() } returns flowOf(models)

        // then
        rule.runBlockingTest {
            useCase.takePhotos().collect { result ->
                assertThat(result).isEqualTo(photos)
            }
        }
    }
}