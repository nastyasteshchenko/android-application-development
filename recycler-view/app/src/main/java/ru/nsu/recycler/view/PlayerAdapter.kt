package ru.nsu.recycler.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import ru.nsu.recycler.view.item.Banner
import ru.nsu.recycler.view.item.Item
import ru.nsu.recycler.view.item.ItemType
import ru.nsu.recycler.view.item.Song

class PlayerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<Item>()

    internal class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<SimpleDraweeView>(R.id.songImage)
        private val titleView = itemView.findViewById<TextView>(R.id.songTitle)
        private val artistView = itemView.findViewById<TextView>(R.id.songAuthor)

        fun bind(song: Song) {
            titleView.text = song.title
            artistView.text = song.artist
            imageView.setImageURI(song.imageUrl)
        }
    }

    internal class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryView = itemView.findViewById<TextView>(R.id.bannerCategory)
        private val titleView = itemView.findViewById<TextView>(R.id.bannerTitle)
        private val descriptionView = itemView.findViewById<TextView>(R.id.bannerDescription)
        private val imageView = itemView.findViewById<SimpleDraweeView>(R.id.bannerImage)

        fun bind(banner: Banner) {
            categoryView.text = banner.category
            titleView.text = banner.title
            descriptionView.text = banner.description
            imageView.setImageURI(banner.imageUrl)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Song -> ItemType.SONG.ordinal
            is Banner -> ItemType.BANNER.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.SONG.ordinal -> SongViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_song, parent, false)
            )

            ItemType.BANNER.ordinal -> BannerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_banner, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SongViewHolder -> holder.bind(items[position] as Song)
            is BannerViewHolder -> holder.bind(items[position] as Banner)
        }
    }

    override fun getItemCount(): Int = items.size
}


