package com.example.poilabschallange.data.repository

import android.graphics.Region
import com.example.poilabschallange.data.remote.dto.home.CountryDto
import retrofit2.Response

interface HomeRepository {
    suspend fun getCountriesAll(): Response<List<CountryDto>>
    suspend fun getCountries(region: String): Response<List<CountryDto>>
}