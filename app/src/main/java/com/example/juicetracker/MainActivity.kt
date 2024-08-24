package com.example.juicetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.juicetracker.ui.JuiceTrackerApp
import com.example.juicetracker.ui.theme.JuiceTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JuiceTrackerApp()
        }
    }
}
