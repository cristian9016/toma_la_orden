package com.cristian_developer.toma_la_orden.data.net

import com.cristian_developer.toma_la_orden.data.model.Plato
import com.cristian_developer.toma_la_orden.data.model.User
import com.cristian_developer.toma_la_orden.data.model.UserLogin
import io.reactivex.Observable
import retrofit2.http.*

interface PlateService {

    @GET("/api/v1/plates")
    fun getPlates(@Header("Authorization") token: String): Observable<ResponseBody<List<Plato>>>

    @POST("/api/v1/plates")
    fun addPlate(@Header("Authorization") token: String, @Body plate: Plato)
            : Observable<ResponseBody<Boolean>>

    @DELETE("/api/v1/plates/{id}")
    fun deletePlate(@Header("Authorization") token: String, @Path("id") id: String)
            : Observable<ResponseBody<Boolean>>

    @POST("/api/v1/plates/{id}/edit")
    fun updatePlate(@Header("Authorization") token: String, @Path("id") id: String, @Body plate: Plato)
            : Observable<ResponseBody<Boolean>>

}