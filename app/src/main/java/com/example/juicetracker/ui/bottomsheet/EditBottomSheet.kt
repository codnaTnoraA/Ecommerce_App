package com.example.juicetracker.ui.bottomsheet

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBottomSheet(
    editScaffoldState: BottomSheetScaffoldState,
    content: @Composable () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = editScaffoldState,
        sheetContent = {
            ButtonRow(
                onCancel = { /*TODO*/ },
                onSubmit = { /*TODO*/ },
                submitButtonEnabled = true
            )
        }
    ) {
        content()
    }
}