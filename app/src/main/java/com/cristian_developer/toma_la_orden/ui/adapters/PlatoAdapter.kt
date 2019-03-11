package com.cristian_developer.toma_la_orden.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Plato
import com.cristian_developer.toma_la_orden.databinding.TemplatePlatoBinding
import com.cristian_developer.toma_la_orden.util.inflate
import io.reactivex.subjects.PublishSubject

class PlatoAdapter : RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder>() {

    var data: MutableList<Plato> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val onClick = PublishSubject.create<Plato>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatoViewHolder =
        PlatoViewHolder(parent.inflate(R.layout.template_plato))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PlatoViewHolder, position: Int) =
        holder.bind(data[position], onClick)


    class PlatoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: TemplatePlatoBinding = DataBindingUtil.bind(view)!!
        fun bind(plato: Plato, onClick: PublishSubject<Plato>) {
            binding.plato = plato
            binding.onClick = onClick
        }
    }


}