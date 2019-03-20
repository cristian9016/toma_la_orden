package com.cristian_developer.toma_la_orden.ui.restaurant_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.ui.adapters.RestaurantAdapter
import com.cristian_developer.toma_la_orden.ui.restaurant_list.add.AddRestauranteActivity
import com.cristian_developer.toma_la_orden.util.LifeDisposable
import com.cristian_developer.toma_la_orden.util.buildViewModel
import com.cristian_developer.toma_la_orden.util.gone
import com.cristian_developer.toma_la_orden.util.visible
import kotlinx.android.synthetic.main.activity_restaurant_list.*
import org.jetbrains.anko.*

class RestaurantList : AppCompatActivity() {

    private val dis = LifeDisposable(this)
    private val viewModel: RestaurantViewModel by lazy { buildViewModel<RestaurantViewModel>() }
    private val restAdapter: RestaurantAdapter by lazy { RestaurantAdapter() }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)

        title = getString(R.string.list_rest)
        resLst.layoutManager = LinearLayoutManager(this)
        resLst.adapter = restAdapter

        restAdapter.editRest
            .subscribe {
                startActivity<AddRestauranteActivity>("rest" to it)
            }

        restAdapter.deleteRest
            .subscribe { rest ->
                alert {
                    title = getString(R.string.alert_text)
                    message = getString(R.string.plate_delete_message)
                    yesButton {
                        loader.visible()
                        dis add viewModel.deleteRestaurant(UserSession.token, rest._id!!)
                            .subscribe(
                                {
                                    loader.gone()
                                    toast(R.string.plate_deleted)
                                    getRest()
                                },
                                {
                                    loader.gone()
                                    toast(it.message!!)
                                }
                            )
                    }
                    noButton { }
                }.show()
            }

    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()
        loader.visible()
        getRest()


        addRest.setOnClickListener {
            startActivity<AddRestauranteActivity>()
        }

        swipeRefresh.setOnRefreshListener {
            getRest()
        }
    }

    private fun getRest() = dis add viewModel.getRestaurants(UserSession.token)
        .subscribe(
            {
                restAdapter.data = it.toMutableList()
                swipeRefresh.isRefreshing = false
                loader.gone()
            },
            {
                longToast(it.message!!)
                swipeRefresh.isRefreshing = false
                loader.gone()
            }
        )
}
