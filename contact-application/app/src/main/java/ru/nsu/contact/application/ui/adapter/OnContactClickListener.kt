package ru.nsu.contact.application.ui.adapter

import ru.nsu.contact.application.ui.item.Item

fun interface OnContactClickListener {
    fun onClickContact(contactItem: Item.ContactItem)
}