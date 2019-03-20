package com.cristian_developer.toma_la_orden.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val nombre: String,
    val celular: String,
    val email: String,
    val password: String,
    val rol: String,
    val restaurante: String? = null
) : Parcelable

class UserLogin(var email: String, var password: String)
class UserResponse(var token: String, var rol: String)