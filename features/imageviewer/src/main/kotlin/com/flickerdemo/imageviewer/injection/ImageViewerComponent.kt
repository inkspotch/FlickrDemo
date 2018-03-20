package com.flickerdemo.imageviewer.injection

import com.flickerdemo.ApplicationComponent
import com.flickerdemo.imageviewer.search.PhotoSearchActivity
import dagger.Component

@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ImageViewerModule::class))
interface ImageViewerComponent {
    fun inject(activity: PhotoSearchActivity)
}