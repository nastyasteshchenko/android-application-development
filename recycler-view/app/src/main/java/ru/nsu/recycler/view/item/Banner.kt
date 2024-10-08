package ru.nsu.recycler.view.item

data class Banner(
    override val id: Int,
    val category: String,
    val title: String,
    val description: String,
    val imageUrl: String
) :
    Item() {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Banner) {
            return false
        }

        return this.id == other.id
                && this.title == other.title
                && this.category == other.category
                && this.description == other.description
                && this.imageUrl == other.imageUrl
    }
}
