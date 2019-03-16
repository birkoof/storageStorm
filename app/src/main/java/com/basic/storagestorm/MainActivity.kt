package com.basic.storagestorm

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_database -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, DatabaseFragment.newInstance(), "databaseFragment")
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, SettingsFragment.newInstance(), "databaseFragment")
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_users -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, UsersFragment.newInstance(), "databaseFragment")
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // display database fragment first on start up
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, DatabaseFragment.newInstance(), "databaseFragment")
                .commit()

            navigation.selectedItemId = R.id.navigation_database
        }
    }
}
