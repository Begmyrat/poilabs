package com.example.poilabschallange.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poilabschallange.domain.common.BaseResult
import com.example.poilabschallange.domain.entity.CountryEntity
import com.example.poilabschallange.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase) : ViewModel() {

    var countries = MutableLiveData<List<CountryEntity>>()
    var countriesOrigin = MutableLiveData<List<CountryEntity>>()
    var isLoading = MutableLiveData<Boolean>(false)
    var isError = MutableLiveData<Boolean>(false)
    var filterType: String? = null
    var sortType: OrderType? = null

    fun getAllCountries(){
        // get all countries
        viewModelScope.launch {
            homeUseCase.getCountriesAll()
                .onStart {
                    // start loading
                    isLoading.value = true
                }
                .catch { exception ->
                    Log.e("CATCH", "exception : $exception")
                    isError.value = true
                    isLoading.value = false
                }
                .collect { baseResult ->
                    isLoading.value = false
                    isError.value = false
                    when(baseResult){
                        is BaseResult.Success -> {
                            countries.postValue(baseResult.data)
                            countriesOrigin.postValue(baseResult.data)
                        }
                        else -> {}
                    }
                }
        }
    }

    fun getCountries(){
        // get countries with region
        viewModelScope.launch {
            filterType?.let {
                homeUseCase.getCountries(it)
                    .onStart {
                        // start loading
                        isLoading.value = true
                    }
                    .catch { exception ->
                        Log.e("CATCH", "exception : $exception")
                        isError.value = true
                        isLoading.value = false
                    }
                    .collect { baseResult ->
                        isLoading.value = false
                        isError.value = false
                        when(baseResult){
                            is BaseResult.Success -> {
                                countries.postValue(baseResult.data)
                                countriesOrigin.postValue(baseResult.data)
                            }
                            else -> {}
                        }
                    }
            }
        }
    }

    fun search(text: String){
        // search countries with given name
        isLoading.value = true
        viewModelScope.launch {
            val list: MutableList<CountryEntity> = mutableListOf()
            countriesOrigin.value?.filter { country -> country.name?.common?.lowercase()?.contains(text.lowercase()) == true || country.name?.official?.lowercase()?.contains(text.lowercase()) == true }
                ?.let { list.addAll(it) }
            countries.postValue(list)
        }
        isLoading.value = false
    }

    fun sort(){
        isLoading.value = true
        viewModelScope.launch {
            // sort by population
            if(sortType == OrderType.POPULATION){
                countries.postValue(
                    countries.value?.sortedBy { it.population }
                )
            } else{ // sort by name
                countries.postValue(
                    countries.value?.sortedBy { it.name?.common }
                )
            }
        }
        isLoading.value = false
    }

}

enum class OrderType{
    POPULATION,
    ALPHABETICALLY
}