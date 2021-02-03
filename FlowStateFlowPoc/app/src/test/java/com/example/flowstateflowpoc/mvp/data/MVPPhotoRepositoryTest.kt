package com.example.flowstateflowpoc.mvp.data

import com.example.flowstateflowpoc.MainCoroutineTestRule
import com.example.flowstateflowpoc.mvp.domain.MVPPhotoContract
import com.example.flowstateflowpoc.mvp.presentation.MVPPhotoPresenter
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MVPPhotoRepositoryTest {
    @get:Rule
    var rule = MainCoroutineTestRule()

    @RelaxedMockK
    private lateinit var view: MVPPhotoContract.View

    @MockK
    private lateinit var useCase: MVPPhotoContract.UseCase
    private lateinit var presenter: MVPPhotoContract.Presenter
    private lateinit var coroutineScope: TestCoroutineScope

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coroutineScope = TestCoroutineScope(TestCoroutineDispatcher())
        presenter = MVPPhotoPresenter(view, useCase, coroutineScope, rule.testDispatcher)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `should take photos`() {
        // given
        val photos = listOf<Photo>(mockk())
        coEvery { useCase.takePhotos() } returns flowOf(photos)

        // when
        rule.runBlockingTest {
            presenter.takePhotos()

            // then
            verifyOrder {
                view.showLoading()
                view.showPhotos(photos)
                view.dismissLoading()
            }

            verify(exactly = 0) { view.showError() }
            verify(exactly = 0) { view.showCancellation() }
        }
    }

    @Test
    fun `should show error when take photos`() {
        // given
        val photos = listOf<Photo>(mockk())
        coEvery { useCase.takePhotos() } returns flow {
            throw RuntimeException("Any exception")
        }

        // when
        rule.runBlockingTest {
            presenter.takePhotos()

            // then
            verifyOrder {
                view.showLoading()
                view.dismissLoading()
                view.showError()
            }

            verify(exactly = 0) { view.showPhotos(any()) }
            verify(exactly = 0) { view.showCancellation() }
        }
    }

    @Test
    fun `should show cancellation when take photos and cancel after some time`() {
        // given
        val photos = listOf<Photo>(mockk())
        coEvery { useCase.takePhotos() } returns flow {
            delay(10_000L)
            emit(photos)
        }

        // when
        rule.runBlockingTest {
            presenter.takePhotos()
            advanceTimeBy(100L)
            presenter.cancelTakePhotos()

            // then
            verifyOrder {
                view.showLoading()
                view.dismissLoading()
                view.showCancellation()
            }

            verify(exactly = 0) { view.showError() }
            verify(exactly = 0) { view.showPhotos(any()) }
        }
    }
}