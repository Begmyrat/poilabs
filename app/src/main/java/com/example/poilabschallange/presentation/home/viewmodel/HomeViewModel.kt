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

    fun getCountries(){
        viewModelScope.launch {
            homeUseCase.getCountries()
                .onStart {
                    // start loading
                }
                .catch { exception ->
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when(baseResult){
                        is BaseResult.Success -> {
                            countries.postValue(baseResult.data)
                        }
                        else -> {}
                    }
                }
        }
    }

}