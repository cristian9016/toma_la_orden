package com.cristian_developer.toma_la_orden.ui.main


import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.databinding.FragmentPendingBinding
import com.cristian_developer.toma_la_orden.ui.adapters.PendingOrderAdapter
import com.cristian_developer.toma_la_orden.ui.main.MainActivity.Companion.dis
import com.cristian_developer.toma_la_orden.ui.main.MainActivity.Companion.viewModel
import com.cristian_developer.toma_la_orden.ui.new_order.NewOrderActivity
import org.jetbrains.anko.toast

class PendingFragment : Fragment() {

    lateinit var binding: FragmentPendingBinding
    private val adapter: PendingOrderAdapter = PendingOrderAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending, container, false)
        binding.recyclerPending.adapter = adapter
        binding.recyclerPending.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getPending()
        binding.addOrder.setOnClickListener {
            startActivity(Intent(context, NewOrderActivity::class.java))
        }
        binding.swipeRefresh.setOnRefreshListener {
            getPending()
        }
    }

    private fun getPending() = dis add viewModel.getPendingOrders(UserSession.token)
        .subscribe(
            {
                adapter.data = it.toMutableList()
                binding.swipeRefresh.isRefreshing = false
            },
            {
                context!!.toast(it.message!!)
                binding.swipeRefresh.isRefreshing = false
            }
        )

    companion object {
        fun instance() = PendingFragment()
    }


}
