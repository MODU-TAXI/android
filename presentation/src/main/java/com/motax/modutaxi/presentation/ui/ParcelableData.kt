package com.motax.modutaxi.presentation.ui

import android.os.Parcel
import android.os.Parcelable

data class MtLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MtLocation> {
        override fun createFromParcel(parcel: Parcel): MtLocation {
            return MtLocation(parcel)
        }

        override fun newArray(size: Int): Array<MtLocation?> {
            return arrayOfNulls(size)
        }
    }


}