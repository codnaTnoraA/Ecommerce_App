package com.example.juicetracker.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.juicetracker.JuiceTrackerApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for JuiceTrackerViewModel
        initializer {
            JuiceTrackerViewModel(juiceTrackerApplication().container.productRepository)
        }
    }
}
/**
 * Extension function to queries for [Application] object and returns an instance of
 * [JuiceTrackerApplication].
 */
fun CreationExtras.juiceTrackerApplication(): JuiceTrackerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as JuiceTrackerApplication)
