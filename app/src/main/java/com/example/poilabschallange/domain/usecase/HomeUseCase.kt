package com.example.poilabschallange.domain.usecase

import com.example.poilabschallange.data.remote.dto.home.*
import com.example.poilabschallange.data.repository.HomeRepository
import com.example.poilabschallange.domain.common.BaseResult
import com.example.poilabschallange.domain.entity.CountryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Collections
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    fun getCountries(): Flow<BaseResult<List<CountryEntity>>> {
        return flow {

            val result = homeRepository.getCountries().body()?.sortedBy { it.name?.official }
            result?.let { Collections.rotate(it, 1) }
            emit(
                BaseResult.Success(
                    result?.map {
                        CountryEntity(
                            name = Name(
                                common = it.name?.common,
                                official = it.name?.official
                            ),
                            independent = it.independent,
                            unMember = it.unMember,
                            currencies = it.currencies,
                            idd = it.idd,
                            capital = it.capital,
                            altSpellings = it.altSpellings,
                            region = it.region,
                            subregion = it.subregion,
                            languages = it.languages,
                            translations = it.translations,
                            latlng = it.latlng,
                            area = it.area,
                            flag = it.flag,
                            maps = it.maps,
                            population = it.population,
                            timezones = it.timezones,
                            continents = it.continents,
                            flags = it.flags,
                            capitalInfo = it.capitalInfo
                        )
                    } ?: listOf()
                )
            )
        }
    }
}