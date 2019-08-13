package com.example.mytestweatherapp.injection


import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mytestweatherapp.data.AppDatabase
import com.example.mytestweatherapp.dataBinding.CityListViewModel
import com.example.mytestweatherapp.dataBinding.MainActivity
import com.example.mytestweatherapp.dataBinding.MapViewModel
import com.example.mytestweatherapp.dataBinding.MapsActivity


class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityListViewModel::class.java)) {
            val db =
                Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "cities")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            @Suppress("UNCHECKED_CAST")
            return CityListViewModel(db.postDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}