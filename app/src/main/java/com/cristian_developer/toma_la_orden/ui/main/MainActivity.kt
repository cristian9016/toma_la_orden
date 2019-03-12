package com.cristian_developer.toma_la_orden.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.cristian_developer.toma_la_orden.R
import com.cristian_developer.toma_la_orden.data.preferences.UserSession
import com.cristian_developer.toma_la_orden.ui.adapters.MainTabsAdapter
import com.cristian_developer.toma_la_orden.ui.list_plates.ListPlatesActivity
import com.cristian_developer.toma_la_orden.ui.login.LoginActivity
import com.cristian_developer.toma_la_orden.util.LifeDisposable
import com.cristian_developer.toma_la_orden.util.buildViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {

    private lateinit var tabAdapter: MainTabsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.order_management_text)
        dis = LifeDisposable(this)
        viewModel = buildViewModel()
        tabAdapter = MainTabsAdapter(supportFragmentManager)
        mainPager.adapter = tabAdapter
        mainTabs.setupWithViewPager(mainPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.mainMenuLogout -> alert {
                title = getString(R.string.alert_text)
                message = getString(R.string.logout_message)
                yesButton {
                    UserSession.logout()
                    startActivity<LoginActivity>()
                    finish()
                }
                noButton { }
            }.show()
            R.id.mainMenuAddItem -> startActivity<ListPlatesActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        lateinit var viewModel: MainActivityViewModel
        lateinit var dis: LifeDisposable
    }
}
