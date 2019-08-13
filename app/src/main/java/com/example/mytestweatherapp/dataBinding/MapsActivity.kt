package com.example.mytestweatherapp.dataBinding

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mytestweatherapp.R
import com.example.mytestweatherapp.api.WeatherApiService
import com.example.mytestweatherapp.data.AppDatabase
import com.example.mytestweatherapp.databinding.ActivityMapsBinding
import com.example.mytestweatherapp.injection.ViewModelMapFactory
import com.example.mytestweatherapp.utils.CITY_ID
import com.example.mytestweatherapp.utils.LOCATION_PERMISSION_REQUEST_CODE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject
import android.location.Criteria
import android.location.LocationManager
import android.annotation.SuppressLint


class MapsActivity : AppCompatActivity() {


    private var permissionEnable: Boolean = false
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: MapViewModel
    @Inject
    lateinit var weatherApi: WeatherApiService
    private lateinit var db: AppDatabase
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  var myLocation : Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_maps
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkPermissionLocation()
        Context.LOCATION_SERVICE
        viewModel =
            ViewModelProviders.of(this, ViewModelMapFactory(this)).get(MapViewModel::class.java)
        viewModel.cityId = intent?.extras?.getLong(CITY_ID, -1)!!
        viewModel.permissionEnable = permissionEnable
        myLocation?.let {   viewModel.myLocation = myLocation}
        viewModel.handleNoCitySelect.observe(this, Observer { showError() })
        viewModel.handleSaveResult.observe(this, Observer { saveAndFinish() })
        binding.viewModel = viewModel
    }

    private fun saveAndFinish() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

    private fun showError() {
        Toast.makeText(this, getString(R.string.no_city_select), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(context: Context, cityId: Long? = null): Intent {
            return Intent(context, MapsActivity::class.java).putExtra(CITY_ID, cityId)
        }
    }

    private fun checkPermissionLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            permissionEnable = true
            getMyLocation()
        }
    }
   @SuppressLint("MissingPermission")
   fun getMyLocation(){
       val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
       val criteria = Criteria()
       val provider = locationManager.getBestProvider(criteria, true)
        myLocation = locationManager.getLastKnownLocation(provider)
   }


}
