package com.example.mytestweatherapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytestweatherapp.model.content.City
import io.reactivex.Completable
import io.reactivex.Flowable


@Dao
interface CitiesDao {
    @get:Query("SELECT * FROM city")
    val all: List<City>

    @Insert
    fun insertAll(vararg city: City)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Query("SELECT * FROM city WHERE id = :id")
    fun getCityById(id: Long?): City
/*    @Query("SELECT * FROM city")
    fun getAllCities(): Flowable<List<City>>*/
}