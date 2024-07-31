package com.example.juicetracker.ui.homescreen.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel

@SuppressLint("NotConstructor", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarHomeScreen(
    exitFun: () -> Unit,
    juiceTrackerViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
//    searchResults: List<Product>,
) {
    //Collecting states from ViewModel
    val searchText by juiceTrackerViewModel.searchText.collectAsState()
    val isSearching by juiceTrackerViewModel.isSearching.collectAsState()
    val productsList by juiceTrackerViewModel.productsList.collectAsState()

//    var text by rememberSaveable { mutableStateOf("") }
    //TODO FILL IN THE ColumnScope WITH THE SEARCH RESULTS
    SearchBar(
        query = searchText,
        onQueryChange = juiceTrackerViewModel::onSearchTextChange,
        onSearch = juiceTrackerViewModel::onSearchTextChange,
        active = isSearching,
        onActiveChange = { juiceTrackerViewModel.onToggleSearch() },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search") },
        tonalElevation = 1.dp,
        leadingIcon = {
            BackButton(modifier = Modifier, onClick = exitFun)
        },
    ) {
        Column {
            Text(text = "asasaas")
        }
    }
}
