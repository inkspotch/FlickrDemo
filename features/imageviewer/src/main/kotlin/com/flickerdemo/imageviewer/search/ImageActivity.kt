package com.flickerdemo.imageviewer.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.flickerdemo.imageviewer.R
import com.flickerdemo.imageviewer.injection.ImageViewerComponent
import com.flickerdemo.imageviewer.injection.InjectableActivity
import com.flickerdemo.imageviewer.util.ImageLoader
import kotlinx.android.synthetic.main.activity_image.*
import javax.inject.Inject

class ImageActivity : InjectableActivity() {
    companion object {
        private const val EXTRA_URL = "url"

        fun create(context: Context, url: String): Intent {
            return Intent(context, ImageActivity::class.java)
                    .putExtra(EXTRA_URL, url)
        }
    }

    @Inject lateinit var imageLoader: ImageLoader

    override fun inject(component: ImageViewerComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val url = intent.getStringExtra(EXTRA_URL)
        imageLoader.load(url, image)
    }
}