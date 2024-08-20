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
package com.example.juicetracker.data

import kotlinx.coroutines.flow.Flow

/**
 * Implementation of [ProductRepository] interface
 * which allow access and modification of Juice items through [JuiceDao]
 */
class RoomProductRepository(override val juiceDao: JuiceDao) : ProductRepository {
    override val productStream: Flow<List<Product>> = juiceDao.getAll()

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
