package ru.nsu.contact.application.ui.adapter

import ru.nsu.contact.application.domain.model.Contact

fun interface OnContactClickListener {
    fun onClickContact(contact: Contact)
}