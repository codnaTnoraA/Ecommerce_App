package com.example.juicetracker.ui.homescreen

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

class AddProductButton

@Composable
fun AddProductButton(
    onClick: () -> Unit,
//    modifier: Modifier? = Modifier
) {
    Button(
        onClick = { onClick() },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Text(text = "Add Product")
    }
}

@Composable
fun EditPriceButton(
    onClick: () -> Unit,
//    modifier: Modifier? = Modifier
) {
    Button(
        onClick = { onClick() },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Text(text = "Edit Price")
    }
}