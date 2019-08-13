package com.example.mytestweatherapp.model.content

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

@Entity
class City() : Parcelable {
    @field:PrimaryKey
    var id:Long? = null
    var name: String? = null
    @Embedded
    @SerializedName("main")
    var weather: Weather? = null
    @Embedded
    var coord : Coord? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
        name = parcel.readString()
        weather = parcel.readParcelable(Weather::class.java.classLoader)
        coord = parcel.readParcelable(Coord::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeParcelable(weather, flags)
        parcel.writeParcelable(coord, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }

}