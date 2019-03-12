package com.cristian_developer.toma_la_orden.ui.main


import android.content.Intent
import android.databinding.DataBindingUtil
import android.hardware.Sensor
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Orden
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.databinding.FragmentPendingBinding
import com.cristian_developer.toma_la_orden.ui.adapters.PendingOrderAdapter
import com.cristian_developer.toma_la_orden.ui.main.MainActivity.Companion.dis
import com.cristian_developer.toma_la_orden.ui.main.MainActivity.Companion.viewModel
import com.cristian_developer.toma_la_orden.ui.new_order.NewOrderActivity
import com.cristian_developer.toma_la_orden.util.gone
import com.cristian_developer.toma_la_orden.util.visible
import com.github.pwittchen.reactivesensors.library.ReactiveSensors
import kotlinx.android.synthetic.main.fragment_completed.*
import kotlinx.android.synthetic.main.fragment_pending.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton


class PendingFragment : Fragment() {

    lateinit var binding: FragmentPendingBinding
    private val adapter: PendingOrderAdapter = PendingOrderAdapter()
    private var cum = 0
    private var repeat = 0
    private var firstTime = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending, container, false)
        binding.recyclerPending.adapter = adapter
        binding.recyclerPending.layoutManager = LinearLayoutManager(context)
        dis add adapter.doubleClick
            .subscribe { orden ->
                context!!.alert {
                    title = "¿Desea Cancelar la orden?"
                    yesButton {
                        progressBarPending.visible()
                        updateOrder(orden, "cancelado")
                    }
                    noButton { }
                }.show()
            }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        progressBarPending.visible()
        getPending()
        firstTime = true

        dis add viewModel.listenSensor(context!!)
            .subscribe {
                cum += 1
                if (cum == 1) {
                    Handler().postDelayed({ cum = 0}, 1000)
                    Handler().postDelayed({ repeat = 0}, 5000)
                    repeat += 1
                    if (!firstTime && repeat == 2) {
                        repeat = 0
                        if (adapter.data.isNotEmpty()) {
                            progressBarPending.visible()
                            updateOrder(adapter.data[0], "completo")
                            context!!.toast("Orden Completa")
                        }
                    } else {
                        if (!firstTime)
                            context!!.toast("Pase de nuevo la mano para confirmar")
                    }
                    firstTime = false
                }
            }

        binding.addOrder.setOnClickListener {
            startActivity(Intent(context, NewOrderActivity::class.java))
        }
        binding.swipeRefresh.setOnRefreshListener {
            getPending()
        }
    }

    private fun getPending() = dis add viewModel.listenPendingOrders(UserSession.token)
        .subscribe(
            {
                progressBarPending.gone()
                adapter.data = it.toMutableList()
                binding.swipeRefresh.isRefreshing = false
            },
            {
                progressBarPending.gone()
                context!!.toast(it.message!!)
                binding.swipeRefresh.isRefreshing = false
            }
        )

    private fun updateOrder(order: Orden, state: String) {
        order.estado = state
        dis add viewModel.updateOrder(UserSession.token, order)
            .subscribe({
                getPending()
            },
                {
                    context!!.toast(it.message!!)
                })
    }

    companion object {
        fun instance() = PendingFragment()
    }


}
