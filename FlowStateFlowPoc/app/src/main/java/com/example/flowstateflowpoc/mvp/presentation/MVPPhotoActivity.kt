package com.example.flowstateflowpoc.mvp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flowstateflowpoc.R
import com.example.flowstateflowpoc.mvp.domain.MVPPhotoContract
import com.example.flowstateflowpoc.mvp.domain.MVPPhotoUseCase
import com.example.flowstateflowpoc.shared.adapter.PhotoAdapter
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import com.example.flowstateflowpoc.shared.extensions.dismiss
import com.example.flowstateflowpoc.shared.extensions.show
import kotlinx.android.synthetic.main.activity_mvp.*
import kotlinx.coroutines.MainScope

class MVPPhotoActivity : AppCompatActivity(), MVPPhotoContract.View {

    private val presenter: MVPPhotoContract.Presenter by lazy {
        MVPPhotoPresenter(
                view = this,
                useCase = MVPPhotoUseCase(),
                coroutineScope = MainScope()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)

        photosRecyclerView.adapter = PhotoAdapter()

        requestButton.setOnClickListener {
            presenter.takePhotos()
        }

        cancelButton.setOnClickListener {
            presenter.cancelTakePhotos()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.willDestroy()
    }

    override fun showLoading() {
        progressBar.show()
        errorText.dismiss()
        photosRecyclerView.dismiss()
        requestButton.isEnabled = false
        cancelButton.isEnabled = true
    }

    override fun dismissLoading() {
        progressBar.dismiss()
        requestButton.isEnabled = true
        cancelButton.isEnabled = false
    }

    override fun showError() {
        showErrorMsg()
    }

    override fun showCancellation() {
        showErrorMsg(true)
    }

    override fun showPhotos(newPhotos: List<Photo>) {
        photosRecyclerView.apply {
            (adapter as PhotoAdapter).photos = newPhotos
            show()
        }
    }

    private fun showErrorMsg(cancellation: Boolean = false) {
        errorText.apply {
            text = if (cancellation) {
                "Got cancellation!!!"
            } else {
                "Something wrong"
            }
            show()
        }
    }
}