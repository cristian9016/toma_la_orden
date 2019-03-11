package com.cristian_developer.toma_la_orden.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Orden
import com.cristian_developer.toma_la_orden.databinding.TemplatePendingOrderBinding
import com.cristian_developer.toma_la_orden.util.inflate

class PendingOrderAdapter : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderHolder>() {

    var data = mutableListOf<Orden>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PendingOrderHolder =
        PendingOrderHolder(parent.inflate(R.layout.template_pending_order))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PendingOrderHolder, position: Int) =
        holder.bind(data[position])

    class PendingOrderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: TemplatePendingOrderBinding = DataBindingUtil.bind(view)!!
        fun bind(orden: Orden) {
            binding.orden = orden
        }
    }
}