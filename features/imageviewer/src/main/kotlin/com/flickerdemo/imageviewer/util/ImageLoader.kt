package com.flickerdemo.imageviewer.util

import android.graphics.drawable.Drawable
import android.os.Handler
import android.widget.ImageView
import com.flickerdemo.imageviewer.R
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

class ImageLoader @Inject constructor(private val client: OkHttpClient, private val handler: Handler) {
    fun load(url: String, view: ImageView) {
        view.setTag(R.id.tag_url, url)

        Thread({
            val request = Request.Builder()
                    .url(url)
                    .build()
            try {
                val response = client.newCall(request).execute()


                val body = response.body()
                if (body != null) {
                    val drawable = Drawable.createFromStream(body.byteStream(), null)

                    handler.post({
                        if (view.getTag(R.id.tag_url) == url) {
                            view.setImageDrawable(drawable)
                        }
                    })
                }
            } catch (ignored: IOException) {
            }
        }).start()
    }
}