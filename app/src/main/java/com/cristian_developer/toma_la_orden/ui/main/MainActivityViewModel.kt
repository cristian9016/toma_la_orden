package com.cristian_developer.toma_la_orden.ui.main

import android.arch.lifecycle.ViewModel
import com.cristian_developer.toma_la_orden.data.model.Orden
import com.cristian_developer.toma_la_orden.data.net.RetrofitSingleton
import com.cristian_developer.toma_la_orden.util.applySchedulers
import com.cristian_developer.toma_la_orden.util.validateResponse


class MainActivityViewModel : ViewModel() {

    private val net = RetrofitSingleton.getOrderService()

    fun getCompletedOrders(token: String) =
        net.getCompleted(token)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun getPendingOrders(token: String) =
        net.getPending(token)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun cancelOrder(token: String, id: String) =
        net.cancelOrder(token, id)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun updateOrder(token: String, id: String, orden: Orden) =
        net.updateOrder(token, id, orden)
            .flatMap { validateResponse(it) }
            .applySchedulers()
}