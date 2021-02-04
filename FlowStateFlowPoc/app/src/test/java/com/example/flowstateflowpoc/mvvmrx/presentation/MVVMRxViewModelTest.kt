package com.example.flowstateflowpoc.mvvmrx.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.flowstateflowpoc.mvvmrx.domain.MVVMRxPhotoContract
import com.example.flowstateflowpoc.shared.Result
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MVVMRxViewModelTest {
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: MVVMRxPhotoContract.UseCase
    private lateinit var viewModel: MVVMRxViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MVVMRxViewModel(useCase, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `should take photos`() {
        // given
        val models = listOf<Photo>(mockk())
        val results = mutableListOf<Result<List<Photo>>>()
        val observer = spyk<Observer<Result<List<Photo>>>>()

        every { useCase.getPhotos() } returns Single.just(models)

        viewModel.state.observeForever(observer)

        // when
        viewModel.takePhotos()

        // then
        verify { observer.onChanged(capture(results)) }
        assertThat(results[0]).isEqualTo(Result.Empty)
        assertThat(results[1]).isEqualTo(Result.Loading)
        assertThat(results[2]).isEqualTo(Result.Success(models))
        assertThat(results[3]).isEqualTo(Result.DismissLoading)
    }

    @Test
    fun `should get error when take photos`() {
        // given
        val results = mutableListOf<Result<List<Photo>>>()
        val observer = spyk<Observer<Result<List<Photo>>>>()

        every { useCase.getPhotos() } returns Single.error(RuntimeException("Any error"))

        viewModel.state.observeForever(observer)

        // when
        viewModel.takePhotos()

        // then
        verify { observer.onChanged(capture(results)) }
        assertThat(results[0]).isEqualTo(Result.Empty)
        assertThat(results[1]).isEqualTo(Result.Loading)
        assertThat(results[2]).isInstanceOf(Result.Error::class.java)
        assertThat(results[3]).isEqualTo(Result.DismissLoading)
    }
}