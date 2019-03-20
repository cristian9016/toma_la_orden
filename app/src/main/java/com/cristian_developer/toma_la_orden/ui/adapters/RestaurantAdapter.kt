package com.cristian_developer.toma_la_orden.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Restaurante
import com.cristian_developer.toma_la_orden.databinding.TemplateRestListBinding
import com.cristian_developer.toma_la_orden.util.inflate
import io.reactivex.subjects.PublishSubject

class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>() {


    val deleteRest: PublishSubject<Restaurante> = PublishSubject.create()
    val editRest: PublishSubject<Restaurante> = PublishSubject.create()
    var data = mutableListOf<Restaurante>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RestaurantHolder =
        RestaurantHolder(p0.inflate(R.layout.template_rest_list))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: RestaurantHolder, p1: Int) =
        p0.bind(data[p1], deleteRest, editRest)

    class RestaurantHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: TemplateRestListBinding = DataBindingUtil.bind(view)!!
        fun bind(rest: Restaurante, deleteRest: PublishSubject<Restaurante>, editRest: PublishSubject<Restaurante>) {
            binding.rest = rest
            binding.delete = deleteRest
            binding.edit = editRest
        }
    }
}