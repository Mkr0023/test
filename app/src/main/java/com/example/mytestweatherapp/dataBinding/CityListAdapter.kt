package com.example.mytestweatherapp.dataBinding


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestweatherapp.R
import com.example.mytestweatherapp.databinding.ItemCityBinding
import com.example.mytestweatherapp.model.content.City


class CityListAdapter(var listener : OnItemClick) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private lateinit var cityList:List<City>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCityBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_city, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(){v -> listener.onItemClick(cityList[position], v) }
        holder.bind(cityList[position])
    }

    override fun getItemCount(): Int {
        return if(::cityList.isInitialized) cityList.size else 0
    }

    fun updateCityList(cityList:List<City>){
        this.cityList = cityList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCityBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = CityViewModel()

        fun bind(city:City){
            viewModel.bind(city)
            binding.viewModel = viewModel
        }
    }
    interface OnItemClick{
       fun onItemClick(item: City, view: View)
    }
}



