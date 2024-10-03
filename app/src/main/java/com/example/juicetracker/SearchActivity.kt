package com.example.juicetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel
import com.example.juicetracker.ui.homescreen.search.SearchBarHomeScreen

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (! Python.isStarted()) {
                Python.start(AndroidPlatform(this))
            }

            val productViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
            productViewModel.updateAllCheckState(false)

            SearchBarHomeScreen(
                {
                    finish()
                    productViewModel.updateAllCheckState(false)
                }
            )

        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }
}