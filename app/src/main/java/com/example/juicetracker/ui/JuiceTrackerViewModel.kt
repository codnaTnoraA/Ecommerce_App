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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juicetracker.data.Product
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.data.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * View Model which maintain states for [JuiceTrackerApp]
 */
class JuiceTrackerViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val emptyProduct = Product(
        id = 0,
        name = "",
        description = "",
        color = JuiceColor.Red.name,
        rating = 3,
        minPrice = 0.0f,
        maxPrice = 0.0f,
        keyword = "",
        checkState = false
    )
    private val _currentJuiceStream = MutableStateFlow(emptyProduct)
    val currentProductStream: StateFlow<Product> = _currentJuiceStream
    val productListStream: Flow<List<Product>> = productRepository.productStream


    fun updateCheckState(checkState: Boolean) {
        viewModelScope.launch { productRepository.updateCheckState(emptyProduct, checkState) }
    }



    // Search ViewModel
    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val productsList = searchText
        .combine(currentProductStream) { text, product ->//combine searchText with _contriesList
            if (text.isBlank()) { //return the entry list of product if not is typed
                product
            } else {
                product
            }
        }.stateIn(//basically convert the Flow returned from combine operator to StateFlow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),//it will allow the StateFlow survive 5 seconds before it been canceled
            initialValue = currentProductStream.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }








    fun resetCurrentJuice() = _currentJuiceStream.update { emptyProduct }
    fun updateCurrentJuice(product: Product) = _currentJuiceStream.update { product }

    fun saveJuice() = viewModelScope.launch {
        if (_currentJuiceStream.value.id > 0) {
            productRepository.updateJuice(_currentJuiceStream.value)
        } else {
            productRepository.addJuice(_currentJuiceStream.value)
        }
    }

    fun deleteJuice(product: Product) = viewModelScope.launch {
        productRepository.deleteJuice(product)
    }

//    fun checkAll(product: Product) = viewModelScope.launch {
//
//    }
//
//    fun uncheckAll(product: Product) = viewModelScope.launch {
//        productDao.check(0)
//    }

}
