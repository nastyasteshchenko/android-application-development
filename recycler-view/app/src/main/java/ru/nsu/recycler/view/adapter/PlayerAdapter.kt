package ru.nsu.recycler.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.recycler.view.PlayerDiffCallback
import ru.nsu.recycler.view.R
import ru.nsu.recycler.view.SongDetailActivity
import ru.nsu.recycler.view.item.Item
import ru.nsu.recycler.view.item.ItemType

class PlayerAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<Item>()

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Item.Song -> ItemType.SONG.typeId
            is Item.Banner -> ItemType.BANNER.typeId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.SONG.typeId -> SongViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_song, parent, false)
            )

            ItemType.BANNER.typeId -> BannerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_banner, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SongViewHolder -> {
                val song = items[position] as Item.Song
                holder.bind(song)
                holder.itemView.setOnClickListener {
                    val intent = Intent(context, SongDetailActivity::class.java)
                    intent.putExtra("title", song.title)
                    intent.putExtra("imageUrl", song.imageUrl)
                    intent.putExtra("author", song.artist)
                    intent.putExtra("description", song.description)
                    context.startActivity(intent)
                }
            }

            is BannerViewHolder -> holder.bind(items[position] as Item.Banner)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Item>) {
        val diffCallback = PlayerDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}


