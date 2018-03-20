package com.flickerdemo.imageviewer.search

import com.flickerdemo.imageviewer.api.SearchService
import com.flickerdemo.imageviewer.api.output.Photo
import com.flickerdemo.imageviewer.api.output.PhotoResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoSearchPresenter(private val view: PhotoSearchView, private val searchService: SearchService, private val text: String) {
    interface PhotoSearchView {
        fun show(photos: Array<Photo>)
        fun showProgressBar(show: Boolean)
        fun showError()
        fun openPhoto(url: String)
    }

    private var totalPages = 0

    fun onCreate(isRecreating: Boolean = false) {
        if (isRecreating) {
            view.showProgressBar(false)
            return
        }

        view.showProgressBar(true)
        searchService.search(text)
                .enqueue(object : Callback<PhotoResults> {
                    override fun onResponse(call: Call<PhotoResults>, response: Response<PhotoResults>) {
                        if (response.isSuccessful) {
                            val body = response.body()

                            if (body != null) {
                                totalPages = body.results.pages
                                view.show(body.results.photos)
                                view.showProgressBar(false)
                                return
                            }
                        }

                        view.showError()
                    }

                    override fun onFailure(call: Call<PhotoResults>, t: Throwable) {
                        view.showError()
                    }
                })
    }

    fun photoClicked(photo: Photo) {
        view.openPhoto(photo.largeUrl())
    }

    fun nextPage(page: Int) {
        if (page > totalPages) return

        searchService.search(text, page)
                .enqueue(object : Callback<PhotoResults> {
                    override fun onResponse(call: Call<PhotoResults>, response: Response<PhotoResults>) {
                        if (response.isSuccessful) {
                            val body = response.body()

                            if (body != null) {
                                view.show(body.results.photos)
                                return
                            }
                        }

                        view.showError()
                    }

                    override fun onFailure(call: Call<PhotoResults>, t: Throwable) {
                        view.showError()
                    }
                })
    }
}