package com.example.juicetracker.ui.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.data.JuiceDao
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CheckAllButton(
    juiceTrackerViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
): Unit {

    // Type List<Product>
    val trackerState by juiceTrackerViewModel.productListStream.collectAsState(emptyList())




    var checkAllButtonCheckedState = remember { mutableStateOf(false) }



//        mutableStateOf(false)


//    val childCheckedStates = remember { productDao.getAllCheckState() }

//    val parentState = when {
//        childCheckedStates.all { it } -> ToggleableState.On
//        childCheckedStates.none { it } -> ToggleableState.Off
//        else -> ToggleableState.Indeterminate
//    }

    Checkbox(
        checked = checkAllButtonCheckedState.value,
        onCheckedChange = { isChecked ->
            checkAllButtonCheckedState.value = isChecked
            juiceTrackerViewModel.updateCheckState(isChecked)
        }
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