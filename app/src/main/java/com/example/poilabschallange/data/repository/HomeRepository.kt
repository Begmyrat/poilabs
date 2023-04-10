package com.example.poilabschallange.data.repository

import com.example.poilabschallange.data.remote.dto.home.CountryDto
import com.example.poilabschallange.data.remote.dto.common.BaseResponse
import retrofit2.Response

interface HomeRepository {
    suspend fun getCountries(): Response<List<CountryDto>>
}