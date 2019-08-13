package com.example.mytestweatherapp.injection.component

import com.example.mytestweatherapp.api.Factory
import com.example.mytestweatherapp.dataBinding.CityListViewModel
import com.example.mytestweatherapp.dataBinding.CityViewModel
import com.example.mytestweatherapp.dataBinding.MapViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(Factory::class)])
interface ViewModelInjector {

    fun inject(cityListViewModel: CityListViewModel)
    fun inject(cityViewModel: CityViewModel)
    fun inject(mapViewModel: MapViewModel)
    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: Factory): Builder
    }
}