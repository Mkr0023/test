package com.example.mytestweatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mytestweatherapp.model.content.City

@Database(entities = [City::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): CitiesDao
}