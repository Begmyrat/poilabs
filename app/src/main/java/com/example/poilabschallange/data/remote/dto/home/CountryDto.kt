package com.example.poilabschallange.data.remote.dto.home

data class CountryDto(
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

data class Name(
    val common: String?,
    val official: String?
)

data class Currency(
    val name: String?,
    val symbol: String?
)

data class Idd(
    val root: String?,
    val suffixes: List<String>?
)

data class Translation(
    val official: String?,
    val common: String?
)

data class Maps(
    val googleMaps: String?,
    val openStreetMaps: String?
)

data class Flag(
    val png: String?,
    val svg: String?,
    val alt: String?
)

data class CapitalInfo(
    val latlng: List<Double>?
)