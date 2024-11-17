package ru.nsu.contact.application.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import ru.nsu.contact.application.R
import ru.nsu.contact.application.domain.model.Contact

class ContactAdapter(private val contactClickListener: OnContactClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var contacts = ArrayList<Contact>()

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.contactName)
        val phoneNumber: TextView = itemView.findViewById(R.id.contactPhoneNumber)
        val image: SimpleDraweeView = itemView.findViewById(R.id.contactImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.name.text = contact.name
        holder.phoneNumber.text = contact.phoneNumber
        holder.image.setImageURI(contact.photoUri)
        holder.itemView.setOnClickListener { contactClickListener.onClickContact(contact) }
    }

    fun updateData(newItems: List<Contact>) {
        val diffCallback = ContactsDiffCallback(contacts, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        contacts.clear()
        contacts.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = contacts.size
}