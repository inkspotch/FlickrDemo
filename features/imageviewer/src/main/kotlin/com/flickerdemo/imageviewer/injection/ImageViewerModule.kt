package com.flickerdemo.imageviewer.injection

import android.os.Handler
import android.os.Looper
import com.flickerdemo.imageviewer.api.SearchService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
class ImageViewerModule {
    @Provides
    fun provideHandler(): Handler {
        return Handler(Looper.getMainLooper())
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }
}