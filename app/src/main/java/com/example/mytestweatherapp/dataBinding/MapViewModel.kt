package com.example.mytestweatherapp.dataBinding

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.mytestweatherapp.api.WeatherApiService
import com.example.mytestweatherapp.data.CitiesDao
import com.example.mytestweatherapp.model.content.City
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.google.android.gms.maps.model.CameraPosition




class MapViewModel(private val citiesDao: CitiesDao, mapFragment: SupportMapFragment) :
    BaseViewModel(), OnMapReadyCallback,
    GoogleMap.OnMapClickListener {
    var myLocation: Location? = null
    var permissionEnable: Boolean = false
    var cityId: Long = -1
    var selectedCity: City? = null
    private lateinit var mMap: GoogleMap
    val handleNoCitySelect: MutableLiveData<Long?> = MutableLiveData()
    val handleSaveResult: MutableLiveData<Long?> = MutableLiveData()

    fun onSaveClick() {
        selectedCity?.let {
            citiesDao.insertCity(it)
            handleSaveResult.value = -1
        } ?: run { handleNoCitySelect.value = -1 }

    }

    @Inject
    lateinit var weatherApi: WeatherApiService

    init {
        mapFragment.getMapAsync(this)
    }

    fun getSaveVisibility(): Int {
        return if (cityId == -1L) View.VISIBLE else View.GONE
    }

    @SuppressLint("CheckResult")
    override fun onMapClick(p0: LatLng?) {
       setByCoord(p0?.latitude, p0?.longitude)
    }
    @SuppressLint("CheckResult")
    fun setByCoord(latitude: Double?, longitude: Double?){
        weatherApi.getWeatherByCoord(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ onRetrieveCitySuccess(it) }, { })
    }
    private fun onRetrieveCitySuccess(result: City) {
        if (cityId == -1L)
            selectedCity = result
        else
            citiesDao.insertCity(result)
        val coord = LatLng(
            result.coord?.lat!!,
            result.coord?.lon!!
        )
        //TODO change to RX
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            mMap.clear()
            mMap.addMarker(
                MarkerOptions().title(getTitleByCity(result)).position(
                    coord
                )
            ).showInfoWindow()
            val cameraPosition = CameraPosition.Builder()
                .target(coord)
                .zoom(7f)
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        p0?.let { mMap = it }
        if (cityId > 0)
            getCityById()
        else {
            mMap.setOnMapClickListener(this)
            if (permissionEnable){
                mMap.isMyLocationEnabled = true
                myLocation?.let {  setByCoord(myLocation?.latitude, myLocation?.longitude)}
                }
        }


    }

    @SuppressLint("CheckResult")
    private fun getCityById() {
        weatherApi.getWeatherByCityId(cityId.toString())
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ onRetrieveCitySuccess(it) }, { })
    }

    private fun getTitleByCity(city: City): String? {
        return city.name + " " + city.weather?.temp + " C"
    }



}