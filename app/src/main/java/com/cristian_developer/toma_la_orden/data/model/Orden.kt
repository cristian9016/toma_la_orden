package com.cristian_developer.toma_la_orden.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Orden(val _id:String?,
            val idUsuario:String?,
            val fecha:Date,
//            val platos:MutableList<Plato>,
            val platos:String,
            val mesa:String,
            var estado:String):Parcelable