package com.basic.storagestorm

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity(), BackpressHandler {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_database -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(active)
                    .show(databaseFragment)
                    .commit()

                active = databaseFragment

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(active)
                    .show(settingsFragment)
                    .commit()

                active = settingsFragment

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(active)
                    .show(searchFragment)
                    .commit()

                active = searchFragment

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val databaseFragment = DatabaseFragment.newInstance()
    private val searchFragment = SearchFragment.newInstance()
    private val settingsFragment = SettingsFragment.newInstance()
    var active: Fragment = databaseFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // display database fragment first on start up
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, searchFragment, "databaseFragment")
            .hide(searchFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, settingsFragment, "databaseFragment")
            .hide(settingsFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, databaseFragment, "databaseFragment")
            .commit()

        active = databaseFragment
        navigation.selectedItemId = R.id.navigation_database
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
    }

    override fun onBackPressed() {
        if (!onBackButtonPressed())
            super.onBackPressed()
    }

    override fun onBackButtonPressed(): Boolean {
        return (active as BackpressHandler).onBackButtonPressed()
    }

    fun toDatabaseFragment() {
        supportFragmentManager
            .beginTransaction()
            .hide(active)
            .show(databaseFragment)
            .commit()

        active = databaseFragment
        navigation.selectedItemId = R.id.navigation_database
    }
}
