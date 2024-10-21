package com.example.juicetracker.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaquo.python.Python
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.data.Product
import com.example.juicetracker.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * View Model which maintain states for [JuiceTrackerApp]
 */
class JuiceTrackerViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val py = Python.getInstance()
    private val testPrint = py.getModule("testPrint")

    private val emptyProduct = Product(
        id = 0,
        name = "",
        description = "",
        color = JuiceColor.Red.name,
        rating = 3,
        minPrice = 0.0f,
        maxPrice = 0.0f,
        keyword = "",
        checkState = false,
        deleteState = false
    )
    private val _currentJuiceStream = MutableStateFlow(emptyProduct)
    val currentProductStream: StateFlow<Product> = _currentJuiceStream
    val productListStream: Flow<List<Product>> = productRepository.productStream

    val testCheckList = productRepository.getCheckList()

    val editButtonState = mutableStateOf(false)

    fun updateAllCheckState(checkState: Boolean) {
        viewModelScope.launch { productRepository.updateAllCheckState(emptyProduct, checkState) }
    }
    fun updateCheckState(checkState: Boolean, productID: Long) {
        viewModelScope.launch { productRepository.updateCheckState(emptyProduct, checkState, productID) }
    }

    val day: MutableState<String> = mutableStateOf("Loading date...")
    val stockPrice: MutableState<String> = mutableStateOf("Loading Stock Price...")

    fun getDate() {
        viewModelScope.launch(Dispatchers.IO) {
            val getYesterday = testPrint["get_yesterday"]
            val _day = getYesterday?.call().toString()
            day.value = _day
        }
    }

    fun getStockPrices() {
        val getUSDStock = testPrint["testFun"]
        viewModelScope.launch {
            productListStream.onEach { productList ->
                println(productList)
                productList.onEach { product ->
                    val usdStock = getUSDStock?.call(product.keyword).toString() // Stock price
                    stockPrice.value = usdStock
                    println(product.keyword)
                }
            }
        }
    }

//    TODO FIX THIS FUNCTIONALITY TO PREVENT DUPLICATES WHEN ADDING PRODUCTS (Product.name won't repeat)
    suspend fun itemDuplicateTrueFalse(product: Product): Boolean {
        return productRepository.duplicateItemTrueFalse(product)
    }

    fun editPrice(minPrice: Float, maxPrice: Float) {
        viewModelScope.launch {
            productRepository.editPrice(minPrice, maxPrice)
        }
    }

//    For deleting a single item
    fun updateDeleteState(deleteState: Boolean, productID: Long) = viewModelScope.launch {
        productRepository.updateDeleteState(
            deleteState = deleteState,
            product = emptyProduct,
            productID = productID
        )
    }

    fun falseDeleteState() = viewModelScope.launch {
        productRepository.falseDeleteState(emptyProduct, false)
    }




    // Search ViewModel
    //state the text typed by the user
    private val _searchText = MutableStateFlow("")
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    val searchText = _searchText.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<Product>> = _searchText
        .flatMapLatest { query -> productRepository.searchProducts(query) }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())




    fun resetCurrentJuice() = _currentJuiceStream.update { emptyProduct }
    fun updateCurrentJuice(product: Product) = _currentJuiceStream.update { product }

    fun saveJuice() = viewModelScope.launch {
        if (_currentJuiceStream.value.id > 0) {
            productRepository.updateJuice(_currentJuiceStream.value)
        } else {
            productRepository.addJuice(_currentJuiceStream.value)
        }
    }

    fun deleteJuice() = viewModelScope.launch {
        productRepository.deleteItem()
    }

    val confirmDeleteState = mutableStateOf(false)
    fun deleteProductConfirm(product: Product) = viewModelScope.launch {
        confirmDeleteState.value = true
    }
}
