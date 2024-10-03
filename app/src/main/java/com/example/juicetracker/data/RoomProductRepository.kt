package com.example.juicetracker.data

import kotlinx.coroutines.flow.Flow

/**
 * Implementation of [ProductRepository] interface
 * which allow access and modification of Juice items through [JuiceDao]
 */
class RoomProductRepository(override val juiceDao: JuiceDao) : ProductRepository {
    override val productStream: Flow<List<Product>> = juiceDao.getAll()

    override fun getCheckList(): Flow<List<Boolean>> = juiceDao.getCheckList()

    override suspend fun addJuice(product: Product) = juiceDao.insert(product)
    override suspend fun deleteJuice(product: Product) = juiceDao.delete(product)
    override suspend fun updateJuice(product: Product) = juiceDao.update(product)

    override suspend fun updateAllCheckState(product: Product, checkState: Boolean) = juiceDao.updateAllCheckState(checkState)

    override suspend fun updateCheckState(product: Product, checkState: Boolean, productID: Long) {
        super.updateCheckState(product, checkState, productID)
        juiceDao.updateCheckState(checkState, productID)
    }

    override suspend fun updateDeleteState(product: Product, deleteState: Boolean, productID: Long) {
        super.updateDeleteState(product, deleteState, productID)
        juiceDao.updateDeleteState(deleteState, productID)
    }

    override suspend fun falseDeleteState(product: Product, deleteState: Boolean) {
        super.falseDeleteState(product, deleteState)
        juiceDao.falseDeleteState()
    }

    override suspend fun deleteItem() {
        juiceDao.deleteItem()
    }
}
