package com.example.mytestweatherapp.dataBinding

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.mytestweatherapp.R
import com.example.mytestweatherapp.api.WeatherApiService
import com.example.mytestweatherapp.data.CitiesDao
import com.example.mytestweatherapp.model.content.City
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CityListViewModel(private val citiesDao: CitiesDao) : BaseViewModel(),
    CityListAdapter.OnItemClick {
    override fun onItemClick(item: City, view: View) {
        handleClick.value = item.id
    }

    @Inject
    lateinit var weatherApi: WeatherApiService
    val cityListAdapter: CityListAdapter = CityListAdapter(this)
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadCity() }
    val handleClick: MutableLiveData<Long?> = MutableLiveData()

    private lateinit var subscription: Disposable

    init {
        loadCity()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadCity() {
        subscription = Observable.fromCallable { citiesDao.all }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveCityListSuccess(result) },
                { onRetrieveCityListError() }
            )
    }

    private fun onRetrieveCityListSuccess(cityList: List<City>) {
        cityListAdapter.updateCityList(cityList)
    }

    private fun onRetrieveCityListError() {
        errorMessage.value = R.string.post_error
    }

    fun onMapOpenClick(view: View) {
        handleClick.value = -1
    }

    fun refreshList() {
            loadCity()
    }
}