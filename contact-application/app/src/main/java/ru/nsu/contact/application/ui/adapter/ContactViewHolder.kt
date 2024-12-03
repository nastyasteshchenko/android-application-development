package ru.nsu.contact.application.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import ru.nsu.contact.application.R
import ru.nsu.contact.application.ui.item.Item

internal class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.contactName)
    private val phoneNumber: TextView = itemView.findViewById(R.id.contactPhoneNumber)
    private val image: SimpleDraweeView = itemView.findViewById(R.id.contactImage)

    fun bind(contact: Item.ContactItem) {
        name.text = contact.name
        phoneNumber.text = contact.phoneNumber
        image.setImageURI(contact.photoUri)
    }
}