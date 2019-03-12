package com.cristian_developer.toma_la_orden.ui.main


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.databinding.FragmentCompletedBinding
import com.cristian_developer.toma_la_orden.ui.adapters.CompleteOrderAdapter
import com.cristian_developer.toma_la_orden.ui.main.MainActivity.Companion.dis
import com.cristian_developer.toma_la_orden.ui.main.MainActivity.Companion.viewModel
import org.jetbrains.anko.toast

class CompletedFragment : Fragment() {

    lateinit var binding: FragmentCompletedBinding
    private var adapter = CompleteOrderAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_completed, container, false)
        binding.recyclerCompleted.adapter = adapter
        binding.recyclerCompleted.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getCompleted()
        binding.swipeRefresh.setOnRefreshListener {
            getCompleted()
        }
    }

    private fun getCompleted() = dis add viewModel.getCompletedOrders(UserSession.token)
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
        fun instance() = CompletedFragment()
    }


}
