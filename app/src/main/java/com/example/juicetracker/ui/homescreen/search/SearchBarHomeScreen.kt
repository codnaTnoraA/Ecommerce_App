package com.example.juicetracker.ui.homescreen.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarHomeScreen(
    searchQuery: String,
//    searchResults: List<Product>,
    onSearchQueryChange: (String) -> Unit
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {},
        active = true,
        onActiveChange = {}
    ) {

    }
}