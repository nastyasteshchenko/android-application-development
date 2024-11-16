package ru.nsu.contact.application.ui

import ru.nsu.contact.application.domain.model.Contact

fun interface OnContactTapListener {
    fun onClickContact(contact: Contact)
}