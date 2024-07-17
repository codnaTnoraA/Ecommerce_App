package com.example.juicetracker.ui.homescreen.search

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.red
import com.example.juicetracker.JuiceTrackerApplication
import com.example.juicetracker.MainActivity
import com.example.juicetracker.R
import com.example.juicetracker.ui.JuiceTrackerApp

@Composable
fun BackButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick() },
        content = {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = stringResource(id = R.string.back_icon)
            )
        },
    )
}