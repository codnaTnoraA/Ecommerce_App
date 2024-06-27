package com.example.juicetracker.ui.homescreen.search

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("NotConstructor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarHomeScreen(
//    searchResults: List<Product>,
) {

    var text by rememberSaveable { mutableStateOf("") }

    BackButton(modifier = Modifier) {
    }

    //TODO FILL IN THE CONTENT PARAMETER WITH THE SEARCH RESULTS
    SearchBar(
        query = text,
        onQueryChange = { text = it },
        onSearch = {},
        active = true,
        onActiveChange = {},
        placeholder = { Text(text = "Search") },
        tonalElevation = 2.dp
    ) {

    }
}

@Preview
@Composable
fun prev() {
    SearchBarHomeScreen()
}