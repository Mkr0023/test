package com.example.mytestweatherapp.dataBinding

import androidx.lifecycle.ViewModel
import com.example.mytestweatherapp.api.Factory
import com.example.mytestweatherapp.injection.component.DaggerViewModelInjector
import com.example.mytestweatherapp.injection.component.ViewModelInjector


abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(Factory)
            .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is CityListViewModel -> injector.inject(this)
            is CityViewModel -> injector.inject(this)
            is MapViewModel -> injector.inject(this)
        }
    }
}