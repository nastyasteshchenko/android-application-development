package ru.nsu.contact.application.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.contact.application.R
import ru.nsu.contact.application.ui.item.Item
import ru.nsu.contact.application.ui.item.ItemType

class ContactAdapter(private val contactClickListener: OnContactClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<Item>()

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Item.ContactItem -> ItemType.CONTACT.typeId
            is Item.BannerItem -> ItemType.BANNER.typeId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.CONTACT.typeId -> ContactViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_item, parent, false)
            )

            ItemType.BANNER.typeId -> BannerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.banner_item, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContactViewHolder -> {
                val contact = items[position] as Item.ContactItem
                holder.bind(contact)
                holder.itemView.setOnClickListener {
                    contactClickListener.onClickContact(
                        contact
                    )
                }
            }

            is BannerViewHolder -> holder.bind(items[position] as Item.BannerItem)
        }
    }

    fun updateData(newItems: ArrayList<Item>) {
        val diffCallback = ContactsDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size
}