package com.motax.modutaxi.presentation.ui

import android.os.Parcel
import android.os.Parcelable

data class MtLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val landMark: String? = "",
    val address: String? = "",
    val isSpot: Boolean = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(landMark)
        parcel.writeString(address)
        parcel.writeByte(if (isSpot) 1 else 0)
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