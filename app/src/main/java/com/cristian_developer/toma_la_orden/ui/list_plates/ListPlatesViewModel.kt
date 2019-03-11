package com.cristian_developer.toma_la_orden.ui.list_plates

import android.arch.lifecycle.ViewModel
import com.cristian_developer.toma_la_orden.data.model.Plato
import com.cristian_developer.toma_la_orden.data.net.RetrofitSingleton
import com.cristian_developer.toma_la_orden.util.applySchedulers
import com.cristian_developer.toma_la_orden.util.validateResponse
import io.reactivex.Observable

class ListPlatesViewModel : ViewModel() {

    private val net = RetrofitSingleton.getPlateService()

    fun getPlates(token: String): Observable<List<Plato>> =
        net.getPlates(token)
            .flatMap { validateResponse(it) }
            .applySchedulers()

    fun deletePlate(token: String, id: String): Observable<Boolean> =
        net.deletePlate(token, id)
            .flatMap { validateResponse(it) }
            .applySchedulers()
}