package com.cristian_developer.toma_la_orden.ui.restaurant_list

import android.arch.lifecycle.ViewModel
import com.cristian_developer.toma_la_orden.data.model.Restaurante
import com.cristian_developer.toma_la_orden.data.net.RetrofitSingleton
import com.cristian_developer.toma_la_orden.util.applySchedulers
import com.cristian_developer.toma_la_orden.util.validateResponse

class RestaurantViewModel : ViewModel() {

    val net = RetrofitSingleton.getRestService()

    fun updateRestaurant(token: String, rest: Restaurante) =
        net.editRestaurant(token, rest._id!!, rest)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun addRestaurant(token: String, rest: Restaurante) =
        net.addRestaurant(token, rest)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun getRestaurants(token: String) =
        net.getRestaurants(token)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun deleteRestaurant(token:String,idRest:String) =
            net.deleteRestaurant(token,idRest)
                .flatMap { validateResponse(it) }
                .applySchedulers()
}