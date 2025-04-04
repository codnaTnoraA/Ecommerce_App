package com.example.juicetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerApp
import com.example.juicetracker.ui.JuiceTrackerViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (! Python.isStarted()) {
                Python.start(AndroidPlatform(this))
            }

            val productViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
            productViewModel.updateAllCheckState(false)

            JuiceTrackerApp()
        }
    }
}
