package com.example.mytestweatherapp.dataBinding

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestweatherapp.R
import com.example.mytestweatherapp.databinding.ActivityMainBinding
import com.example.mytestweatherapp.injection.ViewModelFactory
import com.example.mytestweatherapp.utils.LOCATION_PERMISSION_REQUEST_CODE
import com.example.mytestweatherapp.utils.MAP_REQUEST_CODE
import com.google.android.material.snackbar.Snackbar
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CityListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(CityListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        viewModel.handleClick.observe(this, Observer { handleClick ->
            openMapActivity(handleClick)
        })
        binding.viewModel = viewModel
        checkPermissionLocation()

    }

    private fun openMapActivity(cityId: Long?) {
        startActivityForResult(MapsActivity.newIntent(this, cityId), MAP_REQUEST_CODE)
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }


    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MAP_REQUEST_CODE)
            if (resultCode == Activity.RESULT_OK)
                viewModel.refreshList()
    }
    private fun checkPermissionLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.need_map_access),
                LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            checkPermissionLocation()
        } else return
    }



    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }
}
