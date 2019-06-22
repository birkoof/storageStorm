package com.basic.storagestorm.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.basic.storagestorm.R
import com.basic.storagestorm.fragments.DatabaseFragment
import com.basic.storagestorm.fragments.ManageFragment
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
            R.id.navigation_manage -> {
                supportFragmentManager
                    .beginTransaction()
                    .hide(active)
                    .show(manageFragment)
                    .commit()

                active = manageFragment

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
    private val manageFragment = ManageFragment.newInstance()
    var active: Fragment = databaseFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // display database fragment first on start up
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, searchFragment, "searchFragment")
            .hide(searchFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, manageFragment, "manageFragment")
            .hide(manageFragment)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (active.isVisible) active.onCreateOptionsMenu(menu, menuInflater)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (active.isVisible) active.onOptionsItemSelected(item)
        return true
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
