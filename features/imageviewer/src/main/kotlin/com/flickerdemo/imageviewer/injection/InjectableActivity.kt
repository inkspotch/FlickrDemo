package com.flickerdemo.imageviewer.injection

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.flickerdemo.DemoApp

abstract class InjectableActivity : AppCompatActivity() {

    abstract fun inject(component: ImageViewerComponent);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = DaggerImageViewerComponent.builder()
                .applicationComponent((application as DemoApp).component())
                .imageViewerModule(ImageViewerModule())
                .build()

        inject(component)
    }
}