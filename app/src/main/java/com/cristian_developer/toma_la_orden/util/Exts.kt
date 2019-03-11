package com.cristian_developer.toma_la_orden.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import io.reactivex.Observable
import org.jetbrains.anko.toast

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun EditText.toText() = this.text.toString()

inline fun <reified T : ViewModel> AppCompatActivity.buildViewModel(): T
        = ViewModelProviders.of(this).get(T::class.java)

fun ViewGroup.inflate(layout:Int) = LayoutInflater.from(context).inflate(layout,this,false)

fun AppCompatActivity.validateForm(message: Int,
                                   vararg fields: String): Observable<List<String>>
        = Observable.create<List<String>> {
    if (fields.contains("")) toast(message)
    else it.onNext(fields.toList())
    it.onComplete()
}

fun ImageView.imgBase64(image:String){
    val imageBytes = android.util.Base64.decode(image, android.util.Base64.DEFAULT)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    this.setImageBitmap(decodedImage)
}