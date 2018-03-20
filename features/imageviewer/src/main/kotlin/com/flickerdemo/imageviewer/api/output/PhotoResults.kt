package com.flickerdemo.imageviewer.api.output

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PhotoResults(@SerializedName("photos") val results: PagedResults)

data class PagedResults(val page: Int, val pages: Int, @SerializedName("photo") val photos: Array<Photo>)

data class Photo(val title: String, val farm: Int, val server: String, val id: String, val secret: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(farm)
        parcel.writeString(server)
        parcel.writeString(id)
        parcel.writeString(secret)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

    fun smallUrl(): String {
        return "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_m.jpg"
    }

    fun largeUrl(): String {
        return "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_b.jpg"
    }
}