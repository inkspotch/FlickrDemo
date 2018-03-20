package com.flickerdemo.imageviewer.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.flickerdemo.imageviewer.R
import com.flickerdemo.imageviewer.api.SearchService
import com.flickerdemo.imageviewer.api.output.Photo
import com.flickerdemo.imageviewer.injection.ImageViewerComponent
import com.flickerdemo.imageviewer.injection.InjectableActivity
import com.flickerdemo.imageviewer.util.ImageLoader
import kotlinx.android.synthetic.main.activity_photo_search.*
import javax.inject.Inject

class PhotoSearchActivity : InjectableActivity(), PhotoSearchPresenter.PhotoSearchView {
    companion object {

        private const val EXTRA_SEARCH_TEXT = "search_text"

        fun create(context: Context, searchText: String): Intent {
            return Intent(context, PhotoSearchActivity::class.java)
                    .putExtra(EXTRA_SEARCH_TEXT, searchText)
        }
    }

    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var searchService: SearchService
    private lateinit var presenter: PhotoSearchPresenter

    override fun inject(component: ImageViewerComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo_search)

        val searchText = intent.getStringExtra(EXTRA_SEARCH_TEXT)

        photoList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        photoList.adapter = PhotoListAdapter()
        presenter = PhotoSearchPresenter(this, searchService, searchText)
        presenter.onCreate()
    }

    inner class PhotoListAdapter(private val photos: MutableList<Photo> = ArrayList()) : RecyclerView.Adapter<PhotoViewHolder>() {
        override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
            holder.bind(photos[position])
        }

        override fun getItemCount(): Int {
            return photos.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
            return PhotoViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_photo_view, parent, false))
        }

        fun addPhotos(photos: Array<Photo>) {
            val changedIndex = this.photos.size
            this.photos.addAll(photos)

            notifyItemRangeInserted(changedIndex, photos.size)
        }
    }

    override fun show(photos: Array<Photo>) {
        (photoList.adapter as PhotoListAdapter)
                .addPhotos(photos)
    }

    override fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        photoList.visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun showError() {
        progressBar.visibility = View.GONE
        photoList.visibility = View.GONE
        error.visibility = View.VISIBLE
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)

        fun bind(photo: Photo) {
            image.setImageDrawable(null)

            imageLoader.load(photo.smallUrl(), image)
        }
    }
}


