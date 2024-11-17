package ru.nsu.contact.application.ui

import androidx.recyclerview.widget.DiffUtil
import ru.nsu.contact.application.domain.model.Contact

class ContactsDiffCallback(
    private val oldItems: List<Contact>,
    private val newItems: List<Contact>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}