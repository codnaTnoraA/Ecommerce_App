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
 * Interface for [ProductRepository] which contains method to access and modify juice items
 */
interface ProductRepository {
    val productStream: Flow<List<Product>>

    val juiceDao: JuiceDao
    suspend fun addJuice(product: Product)
    suspend fun deleteJuice(product: Product)
    suspend fun updateJuice(product: Product)
    suspend fun updateAllCheckState(product: Product, checkState: Boolean)  = juiceDao.updateAllCheckState(checkState)

    suspend fun updateCheckState(product: Product, checkState: Boolean, productID: Long) = juiceDao.updateCheckState(checkState, productID)
}
