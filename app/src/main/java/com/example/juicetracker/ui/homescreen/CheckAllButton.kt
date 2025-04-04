package com.example.juicetracker.ui.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CheckAllButton(
    juiceTrackerViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory),
    productCheckList: List<Boolean>
): Unit {

    // Type List<Product>
    val trackerState by juiceTrackerViewModel.productListStream.collectAsState(emptyList())




    var checkAllButtonCheckedState = remember { mutableStateOf(false) }

//    Logic for check all
    if(productCheckList.contains(false)) {
        checkAllButtonCheckedState.value = false
    } else if(productCheckList.all { true }) {
        if(trackerState.isEmpty()) {
            checkAllButtonCheckedState.value = false
        } else {
        checkAllButtonCheckedState.value = true
            }
    }

    Checkbox(
        checked = checkAllButtonCheckedState.value,
        onCheckedChange = { isChecked ->
            checkAllButtonCheckedState.value = isChecked
            juiceTrackerViewModel.updateAllCheckState(isChecked)
        }
    )
}

@Composable
fun CheckAllText(modifier: Modifier? = Modifier) {
    Text(text = "Check All", fontSize = 15.sp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckAllUI(productCheckList: List<Boolean>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CheckAllButton(productCheckList = productCheckList)
        CheckAllText()
    }
}