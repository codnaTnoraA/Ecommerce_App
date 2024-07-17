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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juicetracker.data.Product
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.data.JuiceDao
import com.example.juicetracker.data.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private lateinit var productDao: JuiceDao

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

    fun checkAll(product: Product) = viewModelScope.launch {
        productDao.check(1)
    }

    fun uncheckAll(product: Product) = viewModelScope.launch {
        productDao.check(0)
    }
}
