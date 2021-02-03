package com.example.flowstateflowpoc.main.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flowstateflowpoc.R
import com.example.flowstateflowpoc.shared.extensions.inflate
import kotlinx.android.synthetic.main.holder_main.view.*
import kotlinx.android.synthetic.main.holder_photo.view.*
import kotlinx.android.synthetic.main.holder_photo.view.titleTextView

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var items: List<MainItemHolder> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.holder_main))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(mainItem: MainItemHolder) = with(view) {
            titleTextView.text = mainItem.title
            subtitleTextView.text = mainItem.subtitle
            setOnClickListener { mainItem.action() }
        }
    }
}

class MainItemHolder(
    val title: String,
    val subtitle: String,
    val action: () -> Unit
)