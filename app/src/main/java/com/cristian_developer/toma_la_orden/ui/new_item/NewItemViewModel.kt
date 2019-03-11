package com.cristian_developer.toma_la_orden.ui.new_item

import android.arch.lifecycle.ViewModel
import com.cristian_developer.toma_la_orden.data.model.Plato
import com.cristian_developer.toma_la_orden.data.net.RetrofitSingleton
import com.cristian_developer.toma_la_orden.util.applySchedulers
import com.cristian_developer.toma_la_orden.util.validateResponse
import io.reactivex.Observable

class NewItemViewModel : ViewModel() {

    private val net = RetrofitSingleton.getPlateService()

    fun addPlate(token: String, plate: Plato): Observable<Boolean> =
        net.addPlate(token, plate)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun updatePlate(token: String, plate: Plato,id:String): Observable<Boolean> =
        net.updatePlate(token, id, plate)
            .flatMap { validateResponse(it) }
            .applySchedulers()


}