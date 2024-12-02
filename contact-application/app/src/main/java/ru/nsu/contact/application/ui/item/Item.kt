package ru.nsu.contact.application.ui.item

sealed interface Item {
    val id: Long

    data class BannerItem(
        override val id: Long,
        val category: String,
        val title: String,
        val description: String,
        val imageUrl: String
    ) : Item

    data class ContactItem(
        override val id: Long,
        val name: String,
        val phoneNumber: String,
        val photoUri: String
    ) : Item
}