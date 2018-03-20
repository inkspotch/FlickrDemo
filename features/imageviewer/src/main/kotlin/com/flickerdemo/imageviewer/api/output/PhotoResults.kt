package com.flickerdemo.imageviewer.api.output

import com.google.gson.annotations.SerializedName

data class PhotoResults(@SerializedName("photos") val results: PagedResults)

data class PagedResults(val page: Int, val pages: Int, @SerializedName("photo") val photos: Array<Photo>)

data class Photo(val title: String, val farm: Int, val server: String, val id: String, val secret: String) {
    fun smallUrl(): String {
        return "https://farm" + farm + ".staticflickr.com/" + server +"/" + id + "_" + secret + "_m.jpg"
    }
}