package com.example.juicetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.juicetracker.ui.homescreen.search.SearchBarHomeScreen
import com.example.juicetracker.ui.homescreen.search.SearchTopAppBar
import com.example.juicetracker.ui.theme.JuiceTrackerTheme

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchBarHomeScreen()
//            TODO("MAKE BACK BUTTON ON PHONE REDIRECT TO MAIN ACTIVITY")
        }
    }

    @Preview
    @Composable
    fun Pr() {
        SearchTopAppBar()
    }


}