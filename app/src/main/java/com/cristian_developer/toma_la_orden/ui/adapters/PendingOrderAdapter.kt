package com.cristian_developer.toma_la_orden.ui.adapters

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Orden
import com.cristian_developer.toma_la_orden.databinding.TemplatePendingOrderBinding
import com.cristian_developer.toma_la_orden.util.inflate
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.subjects.PublishSubject

class PendingOrderAdapter : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderHolder>() {

    var data = mutableListOf<Orden>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var doubleClick = PublishSubject.create<Orden>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PendingOrderHolder =
        PendingOrderHolder(parent.inflate(R.layout.template_pending_order))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PendingOrderHolder, position: Int) =
        holder.bind(data[position], doubleClick)

    class PendingOrderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: TemplatePendingOrderBinding = DataBindingUtil.bind(view)!!
        var cum = 0
        @SuppressLint("CheckResult")
        fun bind(orden: Orden, doubleClick: PublishSubject<Orden>) {
            binding.root.clicks()
                .subscribe {
                    cum += 1
                    if (cum == 2) {
                        doubleClick.onNext(orden)
                    } else {
                        Handler().postDelayed({ cum = 0 }, 1000)
                    }

                }
            binding.orden = orden
            if(orden.mesa=="") binding.mesa.setText("Domicilio")
            else binding.mesa.setText("Mesa")
        }
    }
}