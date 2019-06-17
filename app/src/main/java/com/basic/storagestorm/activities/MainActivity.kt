package com.basic.storagestorm.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.basic.storagestorm.R
import com.basic.storagestorm.fragments.CreateFragment
import com.basic.storagestorm.fragments.DatabaseFragment
import com.basic.storagestorm.fragments.SearchFragment
import com.basic.storagestorm.interfaces.BackpressHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BackpressHandler {
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
            R.id.navigation_create -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(active)
                    .show(createFragment)
                    .commit()

                active = createFragment

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
    private val createFragment = CreateFragment.newInstance()
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
            .add(R.id.fragmentContainer, createFragment, "databaseFragment")
            .hide(createFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, databaseFragment, "databaseFragment")
            .commit()

        active = databaseFragment
        navigation.selectedItemId = R.id.navigation_database
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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
