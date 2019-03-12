package com.cristian_developer.toma_la_orden.data.net

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {

    private lateinit var retrofit: Retrofit

    fun init() {
        retrofit = Retrofit.Builder()
//            .baseUrl("http://192.168.1.107:8080")
            .baseUrl("https://fathomless-wave-26084.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    fun getLoginService() = retrofit.create(LoginService::class.java)
    fun getPlateService() = retrofit.create(PlateService::class.java)
    fun getOrderService() = retrofit.create(OrderService::class.java)

}