package com.example.poilabschallange.data.repository.impl

import com.example.poilabschallange.data.remote.api.HomeService
import com.example.poilabschallange.data.remote.dto.home.CountryDto
import com.example.poilabschallange.data.repository.HomeRepository
import retrofit2.Response

class HomeRepositoryImpl(private val service: HomeService): HomeRepository {
    override suspend fun getCountriesAll() = service.getAllCountries()
    override suspend fun getCountries(region: String) = service.getCountries(region)
}