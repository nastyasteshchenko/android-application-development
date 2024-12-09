package ru.nsu.contact.application.domain.model

data class Banner(
    val id: Long,
    val category: String,
    val title: String,
    val description: String,
    val imageUrl: String
)
