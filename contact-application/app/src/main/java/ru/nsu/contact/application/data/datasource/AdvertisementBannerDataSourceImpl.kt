package ru.nsu.contact.application.data.datasource

import ru.nsu.contact.application.data.entity.BannerEntity
import javax.inject.Inject

class AdvertisementBannerDataSourceImpl @Inject constructor() : AdvertisementBannerDataSource {

    companion object {
        private const val BANNER_IMAGE_URL = "https://lyl.su/pfHR"
        private const val BANNER_CATEGORY = "Technology"
        private const val BANNER_TITLE = "Twitter has a new boss"
        private const val BANNER_DESCRIPTION = "Big changes are coming"
        private var id = 0L;
    }

    override fun getBanner(): BannerEntity {
        return BannerEntity(
            id++,
            BANNER_CATEGORY,
            BANNER_TITLE,
            BANNER_DESCRIPTION,
            BANNER_IMAGE_URL
        )
    }
}