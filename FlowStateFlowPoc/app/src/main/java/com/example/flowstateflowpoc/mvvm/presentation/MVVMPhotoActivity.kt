package com.example.flowstateflowpoc.mvvm.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.flowstateflowpoc.R
import com.example.flowstateflowpoc.databinding.ActivityMvvmBinding
import com.example.flowstateflowpoc.mvvm.domain.MVVMPhotoUseCase
import com.example.flowstateflowpoc.shared.Result
import com.example.flowstateflowpoc.shared.adapter.PhotoAdapter
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import com.example.flowstateflowpoc.shared.extensions.dismiss
import com.example.flowstateflowpoc.shared.extensions.show
import kotlinx.android.synthetic.main.activity_mvvm.*
import kotlinx.coroutines.flow.collect

class MVVMPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmBinding
    private val ourViewModel: MVVMPhotoViewModel by lazy {
        MVVMPhotoViewModel(
                useCase = MVVMPhotoUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm)
        binding.apply {
            viewModel = ourViewModel
            lifecycleOwner = this@MVVMPhotoActivity
        }
        photosRecyclerView.adapter = PhotoAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenResumed {
            ourViewModel.state.collect {
                when (it) {
                    Result.Loading -> showLoading()
                    Result.DismissLoading -> dismissLoading()
                    is Result.Error -> showFailed(it.throwable)
                    is Result.Success -> showPhotos(it.data)
                    Result.Empty -> {
                        // not used
                    }
                }
            }
        }
    }

    private fun showPhotos(data: List<Photo>) {
        photosRecyclerView.apply {
            (adapter as PhotoAdapter).photos = data
            show()
        }
    }

    private fun showLoading() {
        progressBar.show()
        errorText.dismiss()
        photosRecyclerView.dismiss()
        requestButton.isEnabled = false
        cancelButton.isEnabled = true
    }

    private fun dismissLoading() {
        progressBar.dismiss()
        requestButton.isEnabled = true
        cancelButton.isEnabled = false
    }

    private fun showFailed(throwable: Throwable?) {
        errorText.apply {
            text = throwable?.message ?: "Empty throwable message"
            show()
        }
    }
}