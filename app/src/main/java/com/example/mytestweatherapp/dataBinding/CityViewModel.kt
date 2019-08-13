package com.example.mytestweatherapp.dataBinding

import androidx.lifecycle.MutableLiveData
import com.example.mytestweatherapp.model.content.City


class CityViewModel:BaseViewModel() {
    private val cityName = MutableLiveData<String>()
    private val cityTemp = MutableLiveData<String>()

    fun bind(city: City){
        cityName.value = city.name
        cityTemp.value = city.weather?.temp.toString()
    }

    fun getCityName():MutableLiveData<String>{
        return cityName
    }

    fun getCityTemp():MutableLiveData<String>{
        return cityTemp
    }
}