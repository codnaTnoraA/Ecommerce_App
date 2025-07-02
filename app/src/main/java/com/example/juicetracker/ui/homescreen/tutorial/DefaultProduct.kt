package com.example.juicetracker.ui.homescreen.tutorial

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.data.Product
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel

val defaultProduct = Product(
    id = 0,
    name = "This is the product name",
    description = "",
    color = JuiceColor.Red.name,
    rating = 3,
    minPrice = 0.0f,
    maxPrice = 50.0f,
    keyword = "Keyword",
    checkState = false,
    deleteState = false
)

@Composable
fun InsertProductFirstLaunch() {
    val model: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
    model.defaultProductSave(defaultProduct)
}