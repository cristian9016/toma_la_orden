package com.cristian_developer.toma_la_orden

import android.app.Application
import com.cristian_developer.toma_la_orden.data.net.RetrofitSingleton
import com.cristian_developer.toma_la_orden.data.preferences.UserSession

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitSingleton.init()
        UserSession.init(this)
    }
}