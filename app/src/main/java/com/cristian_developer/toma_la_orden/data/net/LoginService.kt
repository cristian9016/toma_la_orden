package com.cristian_developer.toma_la_orden.data.net

import com.cristian_developer.toma_la_orden.data.model.User
import com.cristian_developer.toma_la_orden.data.model.UserLogin
import com.cristian_developer.toma_la_orden.data.model.UserResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/api/v1/users/login")
    fun login(@Body user: UserLogin):Observable<ResponseBody<UserResponse>>

    @POST("/api/v1/users/signin")
    fun signin(@Body user: User):Observable<ResponseBody<Boolean>>

}