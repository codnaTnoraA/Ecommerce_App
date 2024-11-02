package com.example.juicetracker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.ui.bottomsheet.EntryBottomSheet
import com.example.juicetracker.ui.homescreen.AddProductButton
import com.example.juicetracker.ui.homescreen.CheckAllUI
import com.example.juicetracker.ui.homescreen.EditPriceButton
import com.example.juicetracker.ui.homescreen.JuiceTrackerList
import com.example.juicetracker.ui.homescreen.JuiceTrackerTopAppBar
import com.example.juicetracker.ui.homescreen.confirmDialogBox.ConfirmDeleteDialogBox
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuiceTrackerApp(
    juiceTrackerViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {



    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )

    val scope = rememberCoroutineScope()
    val trackerState by juiceTrackerViewModel.productListStream.collectAsState(emptyList())

    val testCheckList by juiceTrackerViewModel.testCheckList.collectAsState(emptyList())



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
//        Delete Confirmation function
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

        Scaffold(
            topBar = {
                JuiceTrackerTopAppBar()
            },
            bottomBar = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CheckAllUI()

                    Spacer(modifier = Modifier.weight(1f))
                    if (testCheckList.contains(true)) {
//                        TODO add function to button
                        EditPriceButton {
                            juiceTrackerViewModel.editButtonState.value = true
                            scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                        }
                    } else {
                        AddProductButton(
                            onClick = {
                                juiceTrackerViewModel.editButtonState.value = false
                                juiceTrackerViewModel.resetCurrentJuice()
                                scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                            }
                        )
                    }
                }
            }
        ) { contentPadding ->
            Column(Modifier.padding(contentPadding)) {
                JuiceTrackerList(
                    juiceTrackerViewModel = juiceTrackerViewModel,
                    products = trackerState,
                    onDelete = { juice -> juiceTrackerViewModel.deleteProductConfirm(juice) },
                    onUpdate = { juice ->
                        juiceTrackerViewModel.updateCurrentJuice(juice)
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    },
                )
            }
        }
    }
}
