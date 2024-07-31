/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.ui.bottomsheet.EntryBottomSheet
import com.example.juicetracker.ui.homescreen.AddProductButton
import com.example.juicetracker.ui.homescreen.CheckAllUI
import com.example.juicetracker.ui.homescreen.JuiceTrackerList
import com.example.juicetracker.ui.homescreen.JuiceTrackerTopAppBar
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
        Scaffold(
            topBar = {
                JuiceTrackerTopAppBar()
            },
            bottomBar = {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    CheckAllUI()


                    Spacer(modifier = Modifier.weight(1f))

                    AddProductButton(onClick = {juiceTrackerViewModel.resetCurrentJuice()
                        scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() } }
                    )


                }
            }
        ) { contentPadding ->
            Column(Modifier.padding(contentPadding)) {
                JuiceTrackerList(
                    products = trackerState,
                    onDelete = { juice -> juiceTrackerViewModel.deleteJuice(juice) },
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
