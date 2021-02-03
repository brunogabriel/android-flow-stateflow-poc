package com.example.flowstateflowpoc.mvp.data

import com.example.flowstateflowpoc.MainCoroutineTestRule
import com.example.flowstateflowpoc.mvp.domain.MVPPhotoContract
import com.example.flowstateflowpoc.network.PhotoService
import com.example.flowstateflowpoc.network.models.PhotoResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MVPPhotoRepositoryTest {
    @get:Rule
    var rule = MainCoroutineTestRule()
    @MockK
    private lateinit var service: PhotoService
    private lateinit var repository: MVPPhotoContract.Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = MVPPhotoRepository(service)
    }

    @Test
    fun `should findAll`() {
        // given
        val photos = listOf<PhotoResponse>(mockk())
        coEvery { service.getPhotos() } returns photos

        // then
        rule.runBlockingTest {
            repository.findAll().collect { result ->
                assertThat(result).isEqualTo(photos)
            }
        }
    }
}