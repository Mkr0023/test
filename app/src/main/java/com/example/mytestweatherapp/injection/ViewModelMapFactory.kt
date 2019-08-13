package com.example.mytestweatherapp.injection


import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mytestweatherapp.R
import com.example.mytestweatherapp.data.AppDatabase
import com.example.mytestweatherapp.dataBinding.MapViewModel
import com.google.android.gms.maps.SupportMapFragment


class ViewModelMapFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            val db =
                Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "cities")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(
                db.postDao(),
                activity.supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}