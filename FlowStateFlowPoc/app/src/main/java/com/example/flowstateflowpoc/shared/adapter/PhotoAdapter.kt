package com.example.flowstateflowpoc.shared.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flowstateflowpoc.R
import com.example.flowstateflowpoc.shared.adapter.models.Photo
import com.example.flowstateflowpoc.shared.extensions.inflate
import com.example.flowstateflowpoc.shared.extensions.loadImage
import kotlinx.android.synthetic.main.holder_photo.view.*

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    var photos: List<Photo> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.holder_photo))
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Photo) = with(itemView) {
             titleTextView.text = photo.title
             photoImageView.loadImage(photo.thumbnailUrl)
        }
    }
}