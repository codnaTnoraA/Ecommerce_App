package com.example.juicetracker.ui.homescreen

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.R
import com.example.juicetracker.SearchActivity
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel
import com.example.juicetracker.ui.homescreen.search.SearchBarHomeScreen
import com.example.juicetracker.ui.homescreen.search.SearchButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuiceTrackerTopAppBar(
    modifier: Modifier = Modifier,
    juiceTrackerViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Red,
            titleContentColor = Color.White,
        ),
        title = {
            Row {
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = modifier.weight(1f))
                SearchButton(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                SearchActivity::class.java
                            )
                        )
                    }
                )
            }
        }
    )
}
@Preview
@Composable
fun ajsdbka() {
    JuiceTrackerTopAppBar()
}