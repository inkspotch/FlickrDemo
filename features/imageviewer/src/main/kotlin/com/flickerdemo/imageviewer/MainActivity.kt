package com.flickerdemo.imageviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.flickerdemo.imageviewer.search.PhotoSearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tomatoPhotos.setOnClickListener { _ -> startActivity(PhotoSearchActivity.create(this, "tomato")) }
    }
}

