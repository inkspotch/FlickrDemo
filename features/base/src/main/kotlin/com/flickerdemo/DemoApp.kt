package com.flickerdemo

import android.app.Application

class DemoApp : Application() {

    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule())
                .build()

        component.inject(this)
    }

    fun component(): ApplicationComponent {
        return component
    }
}