package ru.nsu.recycler.view.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import ru.nsu.recycler.view.R
import ru.nsu.recycler.view.item.Item

internal class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView = itemView.findViewById<SimpleDraweeView>(R.id.songImage)
    private val titleView = itemView.findViewById<TextView>(R.id.songTitle)
    private val artistView = itemView.findViewById<TextView>(R.id.songAuthor)

    fun bind(song: Item.Song) {
        titleView.text = song.title
        artistView.text = song.artist
        imageView.setImageURI(song.imageUrl)
    }
}