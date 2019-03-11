package com.cristian_developer.toma_la_orden.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Plato(val _id:String?,
            val idUsuario:String?,
            val nombre:String,
            val foto:String,
            val precio:String):Parcelable
