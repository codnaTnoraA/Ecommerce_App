package com.example.juicetracker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.juicetracker.ui.bottomsheet.EntryBottomSheet
import com.example.juicetracker.ui.homescreen.AddProductButton
import com.example.juicetracker.ui.homescreen.CheckAllUI
import com.example.juicetracker.ui.homescreen.DeleteButton
import com.example.juicetracker.ui.homescreen.EditPriceButton
import com.example.juicetracker.ui.homescreen.JuiceTrackerList
import com.example.juicetracker.ui.homescreen.JuiceTrackerTopAppBar
import com.example.juicetracker.ui.homescreen.confirmDialogBox.ConfirmBatchDeleteDialogBox
import com.example.juicetracker.ui.homescreen.confirmDialogBox.ConfirmDeleteDialogBox
import com.example.juicetracker.ui.homescreen.tutorial.TutorialFloatingActionButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuiceTrackerApp(
    controller: NavHostController,
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

    val productCheckList by juiceTrackerViewModel.testCheckList.collectAsState(emptyList())



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
        },
        bottomSheetHide = { scope.launch { bottomSheetScaffoldState.bottomSheetState.hide() } }
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

        val openBatchDeleteAlertDialog = juiceTrackerViewModel.confirmBatchDeleteState
        when {
            // ...
            openBatchDeleteAlertDialog.value -> {
                ConfirmBatchDeleteDialogBox(
                    onDismissRequest = {
                        openBatchDeleteAlertDialog.value = false
                    },
                    onConfirmation = {
                        openBatchDeleteAlertDialog.value = false
                        juiceTrackerViewModel.batchDelete()
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
                    CheckAllUI(productCheckList)

                    Spacer(modifier = Modifier.weight(1f))
                    if (productCheckList.contains(true)) {
//                        TODO add function to button
                        DeleteButton(
                            onDelete = {
                                juiceTrackerViewModel.batchDeleteConfirm()
                            },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
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
                Spacer(Modifier.weight(1f))
                Row {
                    Spacer(Modifier.weight(1f))
                    TutorialFloatingActionButton(
                        onClick = {controller.navigate("tutorial")},
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
                    )
                }
            }
        }
    }
}
