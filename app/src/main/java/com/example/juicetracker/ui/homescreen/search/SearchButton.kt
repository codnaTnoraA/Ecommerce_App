package com.example.juicetracker.ui.homescreen.search

import androidx.activity.ComponentActivity
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.R
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel
import kotlinx.coroutines.launch


@Composable
fun SearchButton(
    modifier: Modifier,
    onClick: () -> Unit,
    juiceTrackerViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    IconButton(
        onClick = {
            onClick()
          },
        content = {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = stringResource(id = R.string.search_icon)
            )
        },
    )
}

