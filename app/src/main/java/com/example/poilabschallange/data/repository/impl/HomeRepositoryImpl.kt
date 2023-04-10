package com.example.poilabschallange.data.repository.impl

import com.example.poilabschallange.data.remote.api.HomeService
import com.example.poilabschallange.data.repository.HomeRepository

class HomeRepositoryImpl(private val service: HomeService): HomeRepository {
    override suspend fun getCountries() = service.getCountries()
}