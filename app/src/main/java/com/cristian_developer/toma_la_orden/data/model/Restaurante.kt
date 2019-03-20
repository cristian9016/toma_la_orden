package com.cristian_developer.toma_la_orden.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Restaurante(val _id:String?,
                  val idDueno:String?,
                  val foto:String,
                  val nombre:String):Parcelable