package com.example.mytestweatherapp.api

import com.example.mytestweatherapp.model.content.City
import com.example.mytestweatherapp.model.response.WeatherResponse
import com.example.mytestweatherapp.utils.WEATHER
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {

    @GET(WEATHER)
    fun getWeatherByCityId(
        @Query("id") city: String
    ): Observable<City>

    @GET(WEATHER)
    fun getWeatherByCoord(@Query("lat") lat:Double?, @Query("lon") lon: Double?):Observable<City>
}