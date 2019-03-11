package com.cristian_developer.toma_la_orden.util

import android.databinding.BindingAdapter
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


private val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss aaa", Locale.getDefault())


@BindingAdapter("app:imgBase64")
fun imgBase(imageView: ImageView, image: String) {
    if (image != "") {
        val imageBytes = Base64.decode(image, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
        imageView.setImageBitmap(bitmap)
    }
}

@BindingAdapter("app:dateFormat")
fun dateFormat(tv: TextView, date: Date){
    tv.text = format.format(date)
}