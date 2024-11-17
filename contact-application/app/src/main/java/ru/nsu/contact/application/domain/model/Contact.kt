package ru.nsu.contact.application.domain.model

import java.io.Serializable

data class Contact(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val photoUri: String
) : Serializable