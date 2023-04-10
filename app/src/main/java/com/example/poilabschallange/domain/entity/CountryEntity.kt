package com.example.poilabschallange.domain.entity

import com.example.poilabschallange.data.remote.dto.home.*

data class CountryEntity(
    val name: Name?,
    val independent: String?,
    val unMember: Boolean,
    val currencies: Map<String, Currency>?,
    val idd: Idd?,
    val capital: List<String>?,
    val altSpellings: List<String>?,
    val region: String?,
    val subregion: String?,
    val languages: Map<String, String>?,
    val translations: Map<String, Translation>?,
    val latlng: List<Double>?,
    val area: Double?,
    val flag: String?,
    val maps: Maps?,
    val population: Long?,
    val timezones: List<String>?,
    val continents: List<String>?,
    val flags: Flag?,
    val capitalInfo: CapitalInfo?
)