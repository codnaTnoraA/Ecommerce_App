package com.example.juicetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.juicetracker.ui.JuiceTrackerApp
import com.example.juicetracker.ui.homescreen.tutorial.InsertProductFirstLaunch
import com.example.juicetracker.ui.homescreen.tutorial.Tutorial


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences = this.getPreferences(MODE_PRIVATE)?: return

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        setContent {
            val controller = rememberNavController()
            NavHost(navController = controller, startDestination = "mainApp") {
                composable("tutorial") {  Tutorial(controller) }
                composable("mainApp") { JuiceTrackerApp(controller) }
            }
            if(preferences.getBoolean("firstrun", true)) {
                controller.navigate("tutorial")
                preferences.edit().putBoolean("firstrun", false).apply()
                InsertProductFirstLaunch()
            } else {
                controller.navigate("mainApp")
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        val preferences = this.getPreferences(MODE_PRIVATE)?: return
//        if(preferences.getBoolean("firstrun", true)) {
//            setContent {
//                Tutorial()
//            }
//            preferences.edit().putBoolean("firstrun", false).apply()
//        }
//    }
}
