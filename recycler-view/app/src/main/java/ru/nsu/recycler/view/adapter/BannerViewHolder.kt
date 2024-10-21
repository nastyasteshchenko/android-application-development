package ru.nsu.recycler.view.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import ru.nsu.recycler.view.R
import ru.nsu.recycler.view.item.Item

class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val categoryView = itemView.findViewById<TextView>(R.id.bannerCategory)
    private val titleView = itemView.findViewById<TextView>(R.id.bannerTitle)
    private val descriptionView = itemView.findViewById<TextView>(R.id.bannerDescription)
    private val imageView = itemView.findViewById<SimpleDraweeView>(R.id.bannerImage)

    fun bind(banner: Item.Banner) {
        categoryView.text = banner.category
        titleView.text = banner.title
        descriptionView.text = banner.description
        imageView.setImageURI(banner.imageUrl)
    }
}