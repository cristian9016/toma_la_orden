package com.cristian_developer.toma_la_orden.data.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object UserSession {
    private val TOKEN = "token"
    private val ROL = "rol"
    private val ID_RESTAURANTE = "id_res"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("session_preference", Activity.MODE_PRIVATE)
    }

    fun logout() {
        prefs.edit().putString(TOKEN, "")
            .putString(ROL, "")
            .putString(ID_RESTAURANTE, "").apply()
    }

    var token: String
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var rol: String
        get() = prefs.getString(ROL, "")
        set(value) = prefs.edit().putString(ROL, value).apply()

    var idRestaurante: String
        get() = prefs.getString(ID_RESTAURANTE, "")
        set(value) = prefs.edit().putString(ID_RESTAURANTE, value).apply()
}