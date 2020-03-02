package me.wierdest.mindsetquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import me.wierdest.mindsetquest.databinding.ActivityMainBinding

/**
 * Basic activity template set up with databinding,
 * navigation and a navdrawer layout
 */


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        Log.i("MainActivity", "OnCreate")
    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = this.findNavController(R.id.myNavHostFragment)

        Log.i("MainActivity", "onSupportNavigateUp")

        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
