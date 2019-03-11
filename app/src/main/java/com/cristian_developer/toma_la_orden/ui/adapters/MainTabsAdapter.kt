package com.cristian_developer.toma_la_orden.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.cristian_developer.toma_la_orden.ui.main.CompletedFragment
import com.cristian_developer.toma_la_orden.ui.main.PendingFragment

class MainTabsAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(pos: Int): Fragment = when(pos){
        0 -> PendingFragment.instance()
        else -> CompletedFragment.instance()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? =
        when (position) {
            0 -> "En marcha"
            else -> "Completados"
        }


}