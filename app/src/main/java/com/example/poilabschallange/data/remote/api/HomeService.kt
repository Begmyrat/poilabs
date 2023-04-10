package com.example.poilabschallange.data.remote.api

import com.example.poilabschallange.data.remote.dto.home.CountryDto
import com.example.poilabschallange.data.remote.dto.common.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {
    @GET("region/europe")
    suspend fun getCountries()
            : Response<List<CountryDto>>
}