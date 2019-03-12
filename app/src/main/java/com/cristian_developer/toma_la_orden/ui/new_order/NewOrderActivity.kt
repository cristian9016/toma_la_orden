package com.cristian_developer.toma_la_orden.ui.new_order

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.model.Orden
import com.cristian_developer.toma_la_orden.data.model.Plato
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.databinding.TemplateFinishOrderBinding
import com.cristian_developer.toma_la_orden.ui.adapters.PlatoAdapter
import com.cristian_developer.toma_la_orden.ui.adapters.PlatoSelectedAdapter
import com.cristian_developer.toma_la_orden.util.*
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.activity_new_order.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.util.*

class NewOrderActivity : AppCompatActivity() {

    private val listPlatesAdapter = PlatoAdapter()
    private val selectedPlatesAdapter = PlatoSelectedAdapter()
    private val dis = LifeDisposable(this)
    private val viewModel: NewOrderViewModel by lazy { buildViewModel<NewOrderViewModel>() }
    private val selectedList = mutableListOf<Pair<Plato, Int>>()
    private val plateList = mutableListOf<Plato>()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_order)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = "Nueva orden"
        plateSelections.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        plateSelections.adapter = selectedPlatesAdapter
        newOrderPlates.layoutManager = GridLayoutManager(this, 3)
        newOrderPlates.adapter = listPlatesAdapter

        dis add listPlatesAdapter.onClick
            .flatMap {
                plateList.add(it)
                viewModel.calculatePlates(plateList)
            }
            .subscribe {
                selectedPlatesAdapter.data = it
            }
        dis add selectedPlatesAdapter.onClick
            .flatMap {
                plateList.remove(it)
                viewModel.calculatePlates(plateList)
            }
            .subscribe {
                selectedPlatesAdapter.data = it
            }

        dis add btnAcceptOrder.clicks()
            .subscribe {
                alert {
                    val bind: TemplateFinishOrderBinding =
                        DataBindingUtil.inflate(
                            this@NewOrderActivity.layoutInflater,
                            R.layout.template_finish_order,
                            null,
                            false
                        )
                    val orders = StringBuilder()
                    for (order in selectedPlatesAdapter.data) {
                        orders.append("- ", order.second, " ", order.first.nombre, "\n")
                    }
                    bind.orderList.setText(orders.toString())
                    customView = bind.root
                    yesButton {
                        progressBar.visible()
                        dis add viewModel.addOrder(
                            UserSession.token,
                            Orden(null, null, Date(), orders.toString(), bind.tableNumber.toText(), "pendiente")
                        )
                            .subscribe(
                                {
                                    if(it){
                                        progressBar.gone()
                                        toast("Orden Agregada y pendiente")
                                        finish()
                                    }
                                },
                                {
                                    progressBar.gone()
                                    toast(it.message!!)
                                }
                            )
                    }
                    noButton { }
                }.show()
            }

    }

    override fun onResume() {
        super.onResume()
        progressBar.visible()
        btnCancelOrder.setOnClickListener { finish() }
        dis add viewModel.getPlates(UserSession.token)
            .subscribe(
                {
                    progressBar.gone()
                    listPlatesAdapter.data = it.toMutableList()
                },
                {
                    progressBar.gone()
                    toast(it.message!!)
                }
            )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
