package ru.nsu.contact.application.ui.mapper

import ru.nsu.contact.application.domain.model.Banner
import ru.nsu.contact.application.ui.item.Item
import javax.inject.Inject

class BannerItemMapper @Inject constructor() {

    fun bannerToBannerItem(banner: Banner): Item.BannerItem {
        return Item.BannerItem(
            banner.id,
            banner.category,
            banner.title,
            banner.description,
            banner.imageUrl
        )
    }
}