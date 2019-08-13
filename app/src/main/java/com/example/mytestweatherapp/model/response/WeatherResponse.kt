package com.example.mytestweatherapp.model.response
import android.os.Parcel
import android.os.Parcelable
import com.example.mytestweatherapp.model.content.Weather
import com.google.gson.annotations.SerializedName

 class WeatherResponse(
     @SerializedName("main")
    var main: Weather?,
     @SerializedName("cod")
    var code: Int?
) : Parcelable {
     constructor(parcel: Parcel) : this(
         parcel.readParcelable(Weather::class.java.classLoader),
         parcel.readValue(Int::class.java.classLoader) as? Int
     ) {
     }

     override fun writeToParcel(parcel: Parcel, flags: Int) {
         parcel.writeParcelable(main, flags)
         parcel.writeValue(code)
     }

     override fun describeContents(): Int {
         return 0
     }

     companion object CREATOR : Parcelable.Creator<WeatherResponse> {
         override fun createFromParcel(parcel: Parcel): WeatherResponse {
             return WeatherResponse(parcel)
         }

         override fun newArray(size: Int): Array<WeatherResponse?> {
             return arrayOfNulls(size)
         }
     }

 }


