package com.example.mytestweatherapp.model.content

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
data class Weather(
    @SerializedName("humidity")
    var humidity: Double?,
    @SerializedName("pressure")
    var pressure: Double?,
    @SerializedName("temp")
    var temp: Double?,
    @SerializedName("temp_max")
    var tempMax: Double?,
    @SerializedName("temp_min")
    var tempMin: Double?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(humidity)
        parcel.writeValue(pressure)
        parcel.writeValue(temp)
        parcel.writeValue(tempMax)
        parcel.writeValue(tempMin)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }

}
