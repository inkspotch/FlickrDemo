package com.flickerdemo

import dagger.Component
import retrofit2.Retrofit

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(app: DemoApp)

    fun retrofit(): Retrofit
}