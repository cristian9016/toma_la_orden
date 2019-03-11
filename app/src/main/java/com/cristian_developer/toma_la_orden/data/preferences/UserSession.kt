package com.cristian_developer.toma_la_orden.data.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object UserSession {
    private val TOKEN = "token"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("session_preference", Activity.MODE_PRIVATE)
    }
    fun logout(){
        prefs.edit().putString(TOKEN,"").apply()
    }

    var token: String
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()


}