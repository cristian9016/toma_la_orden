package com.cristian_developer.toma_la_orden.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Plato
import com.cristian_developer.toma_la_orden.databinding.TemplateSelectedPlatoBinding
import com.cristian_developer.toma_la_orden.util.inflate
import com.cristian_developer.toma_la_orden.util.visible
import io.reactivex.subjects.PublishSubject

class PlatoSelectedAdapter : RecyclerView.Adapter<PlatoSelectedAdapter.PlatoViewHolder>() {

    var data: MutableList<Pair<Plato,Int>> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val onClick = PublishSubject.create<Plato>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatoViewHolder =
        PlatoViewHolder(parent.inflate(R.layout.template_selected_plato))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PlatoViewHolder, position: Int) =
        holder.bind(data[position], onClick)


    class PlatoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: TemplateSelectedPlatoBinding = DataBindingUtil.bind(view)!!
        fun bind(par:Pair<Plato,Int>, onClick: PublishSubject<Plato>) {
            binding.plato = par.first
            binding.numOrders.visible()
            binding.numOrders.text = par.second.toString()
            binding.onClick = onClick
        }


    }


}