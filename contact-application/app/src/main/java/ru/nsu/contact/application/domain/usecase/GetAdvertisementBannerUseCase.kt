package ru.nsu.contact.application.domain.usecase

import ru.nsu.contact.application.domain.model.Banner
import ru.nsu.contact.application.domain.repository.AdvertisementBannerRepository
import javax.inject.Inject

class GetAdvertisementBannerUseCase
@Inject constructor(private val repository: AdvertisementBannerRepository) {
    fun invoke(): Banner {
        return repository.getBanner()
    }
}