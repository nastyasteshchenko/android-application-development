package ru.nsu.contact.application.data.repository

import ru.nsu.contact.application.data.datasource.AdvertisementBannerDataSource
import ru.nsu.contact.application.data.mapper.BannerEntityMapper
import ru.nsu.contact.application.domain.model.Banner
import ru.nsu.contact.application.domain.repository.AdvertisementBannerRepository
import javax.inject.Inject

class AdvertisementBannerRepositoryImpl
@Inject constructor(
    private val advertisementBannerDataSource: AdvertisementBannerDataSource,
    private val bannerEntityMapper: BannerEntityMapper
) :
    AdvertisementBannerRepository {

    override fun getBanner(): Banner {
        return bannerEntityMapper.bannerEntityToBanner(advertisementBannerDataSource.getBanner())
    }
}