package com.example.modul3xml

import android.os.Parcel
import android.os.Parcelable

data class Lego(
    val id: Int,
    val title: String,
    val year: String,
    val theme: String,
    val pieces: String,
    val description: String,
    val imageRes: Int,
    val webUrl: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeString(theme)
        parcel.writeString(pieces)
        parcel.writeString(description)
        parcel.writeInt(imageRes)
        parcel.writeString(webUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lego> {
        override fun createFromParcel(parcel: Parcel): Lego {
            return Lego(parcel)
        }

        override fun newArray(size: Int): Array<Lego?> {
            return arrayOfNulls(size)
        }
    }
}