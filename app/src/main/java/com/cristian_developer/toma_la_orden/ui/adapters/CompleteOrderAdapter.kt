package com.cristian_developer.toma_la_orden.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Orden
import com.cristian_developer.toma_la_orden.databinding.TemplateCompletedOrderBinding
import com.cristian_developer.toma_la_orden.util.inflate

class CompleteOrderAdapter : RecyclerView.Adapter<CompleteOrderAdapter.CompleteOrderHolder>() {

    var data = mutableListOf<Orden>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CompleteOrderHolder =
        CompleteOrderHolder(parent.inflate(R.layout.template_completed_order))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CompleteOrderHolder, position: Int) =
        holder.bind(data[position])

    class CompleteOrderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: TemplateCompletedOrderBinding = DataBindingUtil.bind(view)!!
        fun bind(orden: Orden) {
            binding.orden = orden
            if(orden.estado == "completo") binding.orderState.setImageResource(R.drawable.ic_order_complete)
            else binding.orderState.setImageResource(R.drawable.ic_order_cancel)
        }
    }
}