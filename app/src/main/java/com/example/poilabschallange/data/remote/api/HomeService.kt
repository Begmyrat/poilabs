package com.example.poilabschallange.data.remote.api

import com.example.poilabschallange.data.remote.dto.home.CountryDto
import com.example.poilabschallange.data.remote.dto.common.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {
    @GET("region/{region}")
    suspend fun getCountries(@Path("region") region: String)
            : Response<List<CountryDto>>

    @GET("all")
    suspend fun getAllCountries()
            : Response<List<CountryDto>>
}