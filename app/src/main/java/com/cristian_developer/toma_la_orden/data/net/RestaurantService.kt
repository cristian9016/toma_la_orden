package com.cristian_developer.toma_la_orden.data.net

import com.cristian_developer.toma_la_orden.data.model.Restaurante
import io.reactivex.Observable
import retrofit2.http.*

interface RestaurantService {

    @POST("/api/v1/restaurants")
    fun addRestaurant(@Header("Authorization") token: String, @Body rest: Restaurante)
            : Observable<ResponseBody<Boolean>>

    @POST("/api/v1/restaurants/{idRest}/edit")
    fun editRestaurant(@Header("Authorization") token: String, @Path("idRest") idRest: String, @Body rest: Restaurante)
            : Observable<ResponseBody<Boolean>>

    @GET("/api/v1/restaurants")
    fun getRestaurants(@Header("Authorization") token: String)
            : Observable<ResponseBody<List<Restaurante>>>

    @DELETE("/api/v1/restaurants/:idRestaurante")
    fun deleteRestaurant(@Header("Authorization") token: String, @Path("idRestaurante") idRest: String)
            : Observable<ResponseBody<Boolean>>


}