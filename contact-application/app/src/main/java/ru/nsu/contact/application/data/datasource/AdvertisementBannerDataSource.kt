package ru.nsu.contact.application.data.datasource

import ru.nsu.contact.application.data.entity.BannerEntity

interface AdvertisementBannerDataSource {
    fun getBanner(): BannerEntity
}