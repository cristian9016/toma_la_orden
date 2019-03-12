package com.cristian_developer.toma_la_orden.data.net

import com.cristian_developer.toma_la_orden.data.model.Orden
import com.cristian_developer.toma_la_orden.data.model.User
import io.reactivex.Observable
import retrofit2.http.*

interface OrderService {

    @GET("/api/v1/orders/{id}/complete")
    fun getCompleted(@Header("Authorization") token: String): Observable<ResponseBody<List<Orden>>>

    @GET("/api/v1/orders/{id}/pending")
    fun getPending(@Header("Authorization") token: String): Observable<ResponseBody<List<Orden>>>

    @POST("/api/v1/orders")
    fun addOrder(@Header("Authorization") token: String, @Body order: Orden): Observable<ResponseBody<Boolean>>

//    @DELETE("/api/v1/orders/{id}")
//    fun cancelOrder(@Header("Authorization") token: String, @Path("id") id: String): Observable<ResponseBody<Boolean>>

    @POST("/api/v1/orders/{id}/edit")
    fun updateOrder(@Header("Authorization") token: String, @Path("id") id: String, @Body orden: Orden)
            : Observable<ResponseBody<Boolean>>

}