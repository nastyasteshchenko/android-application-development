package ru.nsu.contact.application.domain.repository

import ru.nsu.contact.application.domain.model.Banner

interface AdvertisementBannerRepository {
    fun getBanner(): Banner
}