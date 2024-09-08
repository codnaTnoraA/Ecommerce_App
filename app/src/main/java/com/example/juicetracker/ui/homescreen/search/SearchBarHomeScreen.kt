package com.example.juicetracker.ui.homescreen.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel
import com.example.juicetracker.ui.bottomsheet.EntryBottomSheet
import com.example.juicetracker.ui.homescreen.JuiceTrackerList
import com.example.juicetracker.ui.homescreen.confirmDialogBox.ConfirmDeleteDialogBox
import kotlinx.coroutines.launch

@SuppressLint("NotConstructor", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarHomeScreen(
    exitFun: () -> Unit,
    juiceTrackerViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
//    searchResults: List<Product>,
) {
    // Collecting states from ViewModel
    val searchText by juiceTrackerViewModel.searchText.collectAsState()

    //TODO FILL IN THE ColumnScope WITH THE SEARCH RESULTS
    SearchBar(
        query = searchText, // Search Query
        onQueryChange = juiceTrackerViewModel::onSearchTextChange,
        onSearch = { },
        active = true,
        onActiveChange = { },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search") },
        tonalElevation = 1.dp,
        leadingIcon = {
            BackButton(modifier = Modifier, onClick = exitFun)
        },
    ) {
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.Hidden,
                skipHiddenState = false,
            )
        )

        val scope = rememberCoroutineScope()

        EntryBottomSheet(
            juiceTrackerViewModel = juiceTrackerViewModel,
            sheetScaffoldState = bottomSheetScaffoldState,
            modifier = Modifier,
            onCancel = {
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.hide()
                }
            },
            onSubmit = {
                juiceTrackerViewModel.saveJuice()
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.hide()
                }
            }
        ) {
            val openDeleteAlertDialog = juiceTrackerViewModel.confirmDeleteState
            when {
                // ...
                openDeleteAlertDialog.value -> {
                    ConfirmDeleteDialogBox(
                        onDismissRequest = {
                            openDeleteAlertDialog.value = false
                            juiceTrackerViewModel.falseDeleteState()
                        },
                        onConfirmation = {
                            openDeleteAlertDialog.value = false
                            juiceTrackerViewModel.deleteJuice()
                        }
                    )
                }
            }

            val list by juiceTrackerViewModel.searchResults.collectAsState()

            Column {
                JuiceTrackerList(
//                    juiceTrackerViewModel = juiceTrackerViewModel,
                    products = list,
                    onDelete = { product -> juiceTrackerViewModel.deleteProductConfirm(product) },
                    onUpdate = { juice ->
                        juiceTrackerViewModel.updateCurrentJuice(juice)
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                )
            }
        }
    }
}
