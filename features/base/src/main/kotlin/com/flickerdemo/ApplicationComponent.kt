package com.flickerdemo

import android.content.Context
import dagger.Component
import retrofit2.Retrofit

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(app: DemoApp)

    fun context(): Context
    fun retrofit(): Retrofit
}