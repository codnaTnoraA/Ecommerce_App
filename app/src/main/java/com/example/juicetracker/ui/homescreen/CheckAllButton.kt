package com.example.juicetracker.ui.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.sp
import com.example.juicetracker.data.JuiceDao
import kotlinx.coroutines.launch

//private lateinit var productDao: JuiceDao

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CheckAllButton(
    modifier: Modifier? = Modifier,
): Unit {
    val checkAllButtonCheckedState = remember { mutableStateOf(false) }

//    val childCheckedStates = remember { productDao.getAllCheckState() }

//    val parentState = when {
//        childCheckedStates.all { it } -> ToggleableState.On
//        childCheckedStates.none { it } -> ToggleableState.Off
//        else -> ToggleableState.Indeterminate
//    }

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