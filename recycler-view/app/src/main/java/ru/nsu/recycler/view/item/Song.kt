package ru.nsu.recycler.view.item

data class Song(
    override val id: Int,
    val title: String,
    val artist: String,
    val description: String,
    val imageUrl: String
) :
    Item() {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Song) {
            return false
        }

        return this.id == other.id
                && this.title == other.title
                && this.artist == other.artist
                && this.imageUrl == other.imageUrl
                && this.description == other.description
    }
}
