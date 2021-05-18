package com.example.daggerpractice.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.daggerpractice.BaseActivity
import com.example.daggerpractice.R
import com.example.daggerpractice.ui.main.posts.PostsFragment
import com.example.daggerpractice.ui.main.profile.ProfileFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
        NavigationUI.setupWithNavController(nav_view, navController)
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout ->  {
                sessionManager.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_profile -> {
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.main, true).build()

                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.profileScreen, null, navOptions)
            }
            R.id.nav_post -> {
                if(isValidDestination(R.id.postsScreen)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.postsScreen)
                }
            }
            android.R.id.home -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    return true
                } else {
                    return false
                }
            }
        }

        item.isChecked = true
        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }

    private fun isValidDestination(destination: Int): Boolean {
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment).currentDestination?.id
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawer_layout)
    }
}