package ru.nsu.contact.application.domain.model

data class Contact(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val photoUri: String? = null
)