package com.flickerdemo.imageviewer.api

import com.flickerdemo.imageviewer.api.output.PhotoResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("?method=flickr.photos.search&api_key=13743120a62b7bd25dcfa681267d035f&format=json&nojsoncallback=1")
    fun search(@Query("text") text: String, @Query("page") page: Int = 1, @Query("per_page") pageSize: Int = 30): Call<PhotoResults>
}