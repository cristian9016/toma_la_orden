package com.cristian_developer.toma_la_orden.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import java.io.*
import java.net.URLEncoder
import java.util.*
import kotlin.concurrent.thread


object PhotoUtil {

    val processedImg: PublishSubject<Pair<File, String>> = PublishSubject.create()
    private lateinit var fileImage: File

//    fun captureImage(activity: AppCompatActivity): Observable<File> = RxPermissions(activity)
//            .request(Manifest.permission.CAMERA)
//            .flatMap { granted ->
//                Observable.create<File> {
//                    if (granted) {
//                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                        fileImage = File(activity.filesDir, "temp${Date().time}.jpg")
//
//                        val imageUri: Uri = FileProvider.getUriForFile(activity, activity.applicationContext.packageName + ".provider", fileImage)
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//                        activity.startActivityForResult(intent, 135)
//
//                        it.onNext(fileImage)
//                    } else {
//                        activity.toast("Permisos Denegados")
//                    }
//                    it.onComplete()
//                }
//            }

    fun selectImage(activity: AppCompatActivity): Observable<Boolean> = RxPermissions(activity)
        .request(Manifest.permission.READ_EXTERNAL_STORAGE)
        .flatMap { granted ->
            Observable.create<Boolean> {
                if (granted) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    activity.startActivityForResult(
                        Intent.createChooser(
                            intent,
                            "Select Picture"
                        ), 123
                    )
                    //val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    it.onNext(true)
                } else {
                    activity.toast("Permisos Denegados")
                }
                it.onComplete()
            }
        }


    fun processImage(context: Context, requestCode: Int, resultCode: Int, width: Int, height: Int, data: Intent?) {
        if (requestCode == 135 && resultCode == Activity.RESULT_OK) {
            thread {
                val bmOptions = BitmapFactory.Options()
                bmOptions.inJustDecodeBounds = true
                BitmapFactory.decodeFile(fileImage.absolutePath, bmOptions)
                val photoW = bmOptions.outWidth
                val photoH = bmOptions.outHeight

                val scaleFactor = Math.min(photoW / width, photoH / height)

                bmOptions.inJustDecodeBounds = false
                bmOptions.inSampleSize = scaleFactor
                bmOptions.inPurgeable = true

                val bitmap = BitmapFactory.decodeFile(fileImage.absolutePath, bmOptions)
                fileImage.delete()

                val file = File(context.filesDir, "${Date().time}.webp")
                val outStream: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.WEBP, 50, outStream)
                outStream.flush()
                outStream.close()
                context.runOnUiThread {
                    processedImg.onNext(Pair(file, ""))
                }

            }
        } else if (requestCode == 123 && resultCode == Activity.RESULT_OK && data != null) {
            Log.i("URI", "${context.filesDir}${data.data.path}")
            thread {
                var base64 = ""
                val selectedImageUri = data.data
                val parcelFileDescriptor = context.contentResolver.openFileDescriptor(selectedImageUri, "r")
                val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
                val bitmap2 = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor.close()
                val bmWidth = bitmap2.width
                val bmHeight = bitmap2.height
                val matrix = Matrix()
                // RESIZE THE BIT MAP
                val scaleWidth: Float = width.toFloat() / bmWidth
                val scaleHeight: Float = height.toFloat() / bmHeight
                matrix.postScale(scaleWidth, scaleHeight)

                val bitmap = Bitmap.createBitmap(bitmap2, 0, 0, bmWidth, bmHeight, matrix, false)

                val file = File(context.filesDir, "${Date().time}.webp")

                val outStream: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.WEBP, 20, outStream)

                val byteArray = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.WEBP, 20, byteArray)
                try {
                    base64 = Base64.encodeToString(byteArray.toByteArray(), Base64.DEFAULT)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }
                outStream.flush()
                outStream.close()
                context.runOnUiThread {
                    processedImg.onNext(Pair(file, base64))
                }

            }

        }
    }


}