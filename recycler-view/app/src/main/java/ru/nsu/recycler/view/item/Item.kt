package ru.nsu.recycler.view.item

sealed interface Item {
    val id: Int

    data class Banner(
        override val id: Int,
        val category: String,
        val title: String,
        val description: String,
        val imageUrl: String
    ) : Item

    data class Song(
        override val id: Int,
        val title: String,
        val artist: String,
        val description: String,
        val imageUrl: String
    ) : Item
}