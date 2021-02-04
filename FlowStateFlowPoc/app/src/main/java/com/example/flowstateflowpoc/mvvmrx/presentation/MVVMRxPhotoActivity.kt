package com.example.flowstateflowpoc.mvvmrx.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.flowstateflowpoc.R
import com.example.flowstateflowpoc.databinding.ActivityMvvmrxBinding
import com.example.flowstateflowpoc.shared.Result
import com.example.flowstateflowpoc.shared.adapter.PhotoAdapter
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import com.example.flowstateflowpoc.shared.extensions.dismiss
import com.example.flowstateflowpoc.shared.extensions.show
import kotlinx.android.synthetic.main.activity_mvvm.*

class MVVMRxPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmrxBinding

    private val ourViewModel: MVVMRxViewModel by lazy {
        MVVMRxViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvmrx)
        binding.apply {
            viewModel = ourViewModel
            lifecycleOwner = this@MVVMRxPhotoActivity
        }
        photosRecyclerView.adapter = PhotoAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        ourViewModel.state.observe(this) {
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

    override fun onDestroy() {
        ourViewModel.onWillDestroy()
        super.onDestroy()
    }
}