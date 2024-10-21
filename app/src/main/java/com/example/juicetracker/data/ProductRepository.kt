package com.example.juicetracker.data

import kotlinx.coroutines.flow.Flow

/**
 * Interface for [ProductRepository] which contains method to access and modify juice items
 */
interface ProductRepository {
    val productStream: Flow<List<Product>>

    val juiceDao: JuiceDao

    fun getCheckList(): Flow<List<Boolean>>

    suspend fun addJuice(product: Product)
    suspend fun deleteJuice(product: Product)
    suspend fun updateJuice(product: Product)

    fun searchProducts(name: String): Flow<List<Product>> {
        val searchQuery = "%$name%" // Prepare the query with wildcards
        return juiceDao.searchQuery(searchQuery)
    }

    suspend fun editPrice(minPrice: Float, maxPrice: Float)

    suspend fun duplicateItemTrueFalse(product: Product): Boolean {
        return juiceDao.insertItemIfNotExists(product)
    }

    suspend fun updateAllCheckState(product: Product, checkState: Boolean)  = juiceDao.updateAllCheckState(checkState)

    suspend fun updateCheckState(product: Product, checkState: Boolean, productID: Long) = juiceDao.updateCheckState(checkState, productID)
    suspend fun updateDeleteState(product: Product, deleteState: Boolean, productID: Long) = juiceDao.updateDeleteState(deleteState, productID)

    suspend fun falseDeleteState(product: Product, deleteState: Boolean) = juiceDao.falseDeleteState(deleteState)

    suspend fun deleteItem() = juiceDao.deleteItem()
}
