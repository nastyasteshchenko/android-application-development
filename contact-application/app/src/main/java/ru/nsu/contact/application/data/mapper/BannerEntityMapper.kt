package ru.nsu.contact.application.data.mapper

import ru.nsu.contact.application.data.entity.BannerEntity
import ru.nsu.contact.application.domain.model.Banner
import javax.inject.Inject

class BannerEntityMapper @Inject constructor() {

    fun bannerEntityToBanner(bannerEntity: BannerEntity): Banner {
        return Banner(
            bannerEntity.id,
            bannerEntity.category,
            bannerEntity.title,
            bannerEntity.description,
            bannerEntity.imageUrl
        )
    }
}