package com.example.juicetracker.ui.homescreen

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun CheckAllButton(modifier: Modifier? = Modifier): Unit {
    var checkAllButtonCheckedState = remember { mutableStateOf(false) }

    Checkbox(
        checked = checkAllButtonCheckedState.value,
        onCheckedChange = { checkAllButtonCheckedState.value = it }
    )
}

@Composable
fun CheckAllText(modifier: Modifier? = Modifier) {
    Text(text = "Check All", fontSize = 15.sp)
}

@Composable
fun CheckAllUI() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CheckAllButton()
        CheckAllText()
    }
}