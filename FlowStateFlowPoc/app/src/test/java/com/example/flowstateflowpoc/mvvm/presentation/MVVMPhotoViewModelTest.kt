package com.example.flowstateflowpoc.mvvm.presentation

import com.example.flowstateflowpoc.MainCoroutineTestRule
import com.example.flowstateflowpoc.mvvm.domain.MVVMPhotoContract
import com.example.flowstateflowpoc.shared.Result
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MVVMPhotoViewModelTest {
    @get:Rule
    val rule = MainCoroutineTestRule()

    @MockK
    private lateinit var useCase: MVVMPhotoContract.UseCase
    private lateinit var viewModel: MVVMPhotoViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MVVMPhotoViewModel(useCase, rule.testDispatcher)
    }

    @Test
    fun `should take photos`() {
        // given
        val photos = listOf<Photo>(mockk())
        coEvery { useCase.takePhotos() } returns flowOf(photos)
        val result = mutableListOf<Result<List<Photo>>>()

        // when
        rule.runBlockingTest {
            val testJob = launch { viewModel.state.toList(result) }
            viewModel.takePhotos()

            // then
            assertThat(result[0]).isEqualTo(Result.Empty)
            assertThat(result[1]).isEqualTo(Result.Loading)
            assertThat(result[2]).isEqualTo(Result.Success(photos))
            assertThat(result[3]).isEqualTo(Result.DismissLoading)

            testJob.cancel()
        }
    }

    @Test
    fun `should got error when take photos`() {
        // given
        val photos = listOf<Photo>(mockk())
        coEvery { useCase.takePhotos() } returns flow {
            throw RuntimeException("Any exception")
        }
        val result = mutableListOf<Result<List<Photo>>>()

        // when
        rule.runBlockingTest {
            val testJob = launch { viewModel.state.toList(result) }
            viewModel.takePhotos()

            // then
            assertThat(result[0]).isEqualTo(Result.Empty)
            assertThat(result[1]).isEqualTo(Result.Loading)
            assertThat(result[2]).isEqualTo(Result.DismissLoading)
            assertThat(result[3]).isInstanceOf(Result.Error::class.java)

            testJob.cancel()
        }
    }

    @Test
    fun `should got cancellation when take photos`() {
        // given
        val photos = listOf<Photo>(mockk())
        coEvery { useCase.takePhotos() } returns flow {
            delay(10_000L)
        }
        val result = mutableListOf<Result<List<Photo>>>()

        // when
        rule.runBlockingTest {
            val testJob = launch { viewModel.state.toList(result) }
            viewModel.takePhotos()
            advanceTimeBy(100L)
            viewModel.cancelTakePhotos()

            // then
            assertThat(result[0]).isEqualTo(Result.Empty)
            assertThat(result[1]).isEqualTo(Result.Loading)
            assertThat(result[2]).isEqualTo(Result.DismissLoading)
            assertThat(result[3]).isInstanceOf(Result.Error::class.java)

            testJob.cancel()
        }
    }
}