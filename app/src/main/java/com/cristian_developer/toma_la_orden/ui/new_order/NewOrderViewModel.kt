package com.cristian_developer.toma_la_orden.ui.new_order

import android.arch.lifecycle.ViewModel
import com.cristian_developer.toma_la_orden.data.model.Orden
import com.cristian_developer.toma_la_orden.data.model.Plato
import com.cristian_developer.toma_la_orden.data.net.RetrofitSingleton
import com.cristian_developer.toma_la_orden.util.applySchedulers
import com.cristian_developer.toma_la_orden.util.validateResponse
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable

class NewOrderViewModel : ViewModel() {

    private val netPlate = RetrofitSingleton.getPlateService()
    private val netOrder = RetrofitSingleton.getOrderService()

    fun getPlates(token: String): Observable<List<Plato>> =
        netPlate.getPlates(token)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun addOrder(token: String, order: Orden): Observable<Boolean> =
        netOrder.addOrder(token, order)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun calculatePlates(list: MutableList<Plato>) =
        list.toObservable()
            .groupBy { it }
            .flatMapSingle {
                it.count()
                    .map { count -> Pair(it.key!!, count.toInt()) }
            }
            .toList()
            .toObservable()
            .applySchedulers()

}