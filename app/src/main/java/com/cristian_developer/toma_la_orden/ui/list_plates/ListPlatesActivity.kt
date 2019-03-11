package com.cristian_developer.toma_la_orden.ui.list_plates

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.ui.adapters.ListPlatoAdapter
import com.cristian_developer.toma_la_orden.ui.adapters.PlatoAdapter
import com.cristian_developer.toma_la_orden.ui.new_item.NewItemActivity
import com.cristian_developer.toma_la_orden.util.LifeDisposable
import com.cristian_developer.toma_la_orden.util.buildViewModel
import com.cristian_developer.toma_la_orden.util.gone
import com.cristian_developer.toma_la_orden.util.visible
import kotlinx.android.synthetic.main.activity_list_plates.*
import org.jetbrains.anko.*

class ListPlatesActivity : AppCompatActivity() {

    private val dis = LifeDisposable(this)
    private val viewModel: ListPlatesViewModel by lazy { buildViewModel<ListPlatesViewModel>() }
    private val platoAdapter: ListPlatoAdapter by lazy { ListPlatoAdapter() }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_plates)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.list_plate)
        listPlates.layoutManager = LinearLayoutManager(this)
        listPlates.adapter = platoAdapter

        platoAdapter.onClickEdit
            .subscribe {
                startActivity<NewItemActivity>("plato" to it)
            }

        platoAdapter.onClickDelete
            .subscribe { plato ->
                alert {
                    title = getString(R.string.alert_text)
                    message = getString(R.string.plate_delete_message)
                    yesButton {
                        loader.visible()
                        dis add viewModel.deletePlate(UserSession.token, plato._id!!)
                            .subscribe(
                                {
                                    loader.gone()
                                    toast(R.string.plate_deleted)
                                    getPlates()
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
        getPlates()

        addPlate.setOnClickListener {
            startActivity<NewItemActivity>()
        }

        swipeRefresh.setOnRefreshListener {
            getPlates()
        }
    }

    private fun getPlates() = dis add viewModel.getPlates(UserSession.token)
        .subscribe(
            {
                platoAdapter.data = it.toMutableList()
                swipeRefresh.isRefreshing = false
                loader.gone()
            },
            {
                longToast(it.message!!)
                swipeRefresh.isRefreshing = false
                loader.gone()
            }
        )

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
