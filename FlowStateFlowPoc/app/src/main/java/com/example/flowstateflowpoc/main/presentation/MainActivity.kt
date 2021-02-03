package com.example.flowstateflowpoc.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.flowstateflowpoc.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainAdapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
    }

    private fun setUpView() {
        mainAdapter = MainAdapter().apply {
            items = listOf(
                MainItemHolder("MVVM - Flow", "Model View View-Model") {

                },
                MainItemHolder("MVP - Flow", "Model View Presenter") {

                },
                MainItemHolder("MVVM - Rx", "Model View View-Model") {

                },
            )
        }

        mainRecyclerView.apply {
            adapter = mainAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}