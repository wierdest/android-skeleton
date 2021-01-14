package me.wierdest.myapplication

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.math.MathUtils
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import me.wierdest.myapplication.databinding.ActivityMainBinding
import me.wierdest.myapplication.home.MAX_SCROLL_TIME_IN_MILLI
import me.wierdest.myapplication.home.MIN_SCROLL_TIME_IN_MILLI
import me.wierdest.myapplication.settings.DEFAULT_SCROLL_SPEED
import me.wierdest.myapplication.utilities.linearConversion

/**
 * Basic activity template set up with databinding,
 * navigation and a navdrawer layout
 */


class MainActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        /**
         * App shared preferences
         */
        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        Log.i("MainActivity", "OnCreate")
    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = this.findNavController(R.id.myNavHostFragment)

        Log.i("MainActivity", "onSupportNavigateUp")

        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val action = event.action
        val keyCode = event.keyCode

        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (action == KeyEvent.ACTION_DOWN) {
                    val new = preferences.getInt("speed", DEFAULT_SCROLL_SPEED) + 1
                    with(preferences.edit()) {
                        putInt("speed", new)
                        putLong("time",
                            MathUtils.clamp(
                                new.linearConversion(100, 0, MIN_SCROLL_TIME_IN_MILLI, MAX_SCROLL_TIME_IN_MILLI).toLong(),
                                0L,
                                MAX_SCROLL_TIME_IN_MILLI.toLong()
                            )
                        )
                        apply()
                    }
                    Log.i(TAG, "reset speed to: $new")

                }
                true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (action == KeyEvent.ACTION_DOWN) {
                    val new = preferences.getInt("speed", DEFAULT_SCROLL_SPEED) - 1
                    with(preferences.edit()) {
                        putInt("speed", new)
                        putLong("time",
                            MathUtils.clamp(
                                new.linearConversion(100, 0, MIN_SCROLL_TIME_IN_MILLI, MAX_SCROLL_TIME_IN_MILLI).toLong(),
                                0L,
                                MAX_SCROLL_TIME_IN_MILLI.toLong()
                            )
                        )
                        apply()

                    }
                    Log.i(TAG, "reset speed to: $new reset time to")
                }
                true
            }
            else -> super.dispatchKeyEvent(event)
        }
    }
}
