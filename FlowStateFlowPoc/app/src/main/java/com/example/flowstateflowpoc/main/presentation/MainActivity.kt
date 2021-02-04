package com.example.flowstateflowpoc.main.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.flowstateflowpoc.R
import com.example.flowstateflowpoc.mvp.presentation.MVPPhotoActivity
import com.example.flowstateflowpoc.mvvm.presentation.MVVMPhotoActivity
import com.example.flowstateflowpoc.mvvmrx.presentation.MVVMRxPhotoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
    }

    private fun setUpView() {
        mainRecyclerView.apply {
            adapter = MainAdapter().apply {
                items = listOf(
                        MainItemHolder("MVVM - Flow", "Model View View-Model") {
                            startScreen(MVVMPhotoActivity::class.java)
                        },
                        MainItemHolder("MVP - Flow", "Model View Presenter") {
                            startScreen(MVPPhotoActivity::class.java)
                        },
                        MainItemHolder("MVVM - Rx", "Model View View-Model") {
                            startScreen(MVVMRxPhotoActivity::class.java)
                        },
                )
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun <T>startScreen(clazz: Class<T>) {
        startActivity(Intent(
                this,
                clazz
        ))
    }
}