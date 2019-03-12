package com.cristian_developer.toma_la_orden.ui.main

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.hardware.Sensor
import com.cristian_developer.toma_la_orden.data.model.Orden
import com.cristian_developer.toma_la_orden.data.net.RetrofitSingleton
import com.cristian_developer.toma_la_orden.util.applySchedulers
import com.cristian_developer.toma_la_orden.util.validateResponse
import com.github.pwittchen.reactivesensors.library.ReactiveSensorFilter
import com.github.pwittchen.reactivesensors.library.ReactiveSensors


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

    fun updateOrder(token: String, orden: Orden) =
        net.updateOrder(token, orden._id!!, orden)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun listenSensor(context: Context) = ReactiveSensors(context)
        .observeSensor(Sensor.TYPE_PROXIMITY)
        .filter(ReactiveSensorFilter.filterSensorChanged())
        .applySchedulers()


}