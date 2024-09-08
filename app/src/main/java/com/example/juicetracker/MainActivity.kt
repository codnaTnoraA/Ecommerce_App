package com.example.juicetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.juicetracker.ui.JuiceTrackerApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (! Python.isStarted()) {
                Python.start(AndroidPlatform(this))
            }
            JuiceTrackerApp()
        }
    }
}
