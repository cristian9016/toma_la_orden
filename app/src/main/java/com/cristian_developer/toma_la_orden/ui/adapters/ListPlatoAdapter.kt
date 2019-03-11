package com.cristian_developer.toma_la_orden.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Plato
import com.cristian_developer.toma_la_orden.databinding.TemplateEditPlatoBinding
import com.cristian_developer.toma_la_orden.util.inflate
import io.reactivex.subjects.PublishSubject

class ListPlatoAdapter : RecyclerView.Adapter<ListPlatoAdapter.PlatoViewHolder>() {

    var data: MutableList<Plato> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val onClickEdit:PublishSubject<Plato> = PublishSubject.create()
    val onClickDelete:PublishSubject<Plato> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatoViewHolder =
        PlatoViewHolder(parent.inflate(R.layout.template_edit_plato))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PlatoViewHolder, position: Int) =
        holder.bind(data[position],onClickEdit,onClickDelete)

    class PlatoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: TemplateEditPlatoBinding = DataBindingUtil.bind(view)!!
        fun bind(plato: Plato, onClickEdit:PublishSubject<Plato>,onClickDelete:PublishSubject<Plato>) {
            binding.plato = plato
            binding.editPlato = onClickEdit
            binding.deletePlato = onClickDelete
        }
    }
}