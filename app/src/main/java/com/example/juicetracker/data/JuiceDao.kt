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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Juice Data Access Object which contains methods to access and modify Juice table in Room DB
 */
@Dao
interface JuiceDao {
    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Insert
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("SELECT * FROM product WHERE name LIKE :name")
    fun searchQuery(name: String): Flow<List<Product>>

    @Query("UPDATE product SET checkState=:checkState")
    suspend fun updateAllCheckState(checkState: Boolean)

    @Query("UPDATE product SET checkState=:checkState WHERE id=:productID")
    suspend fun updateCheckState(checkState: Boolean, productID: Long)

//    Used to delete single items
    @Query("UPDATE product SET deleteState=:deleteState WHERE id=:productID")
    suspend fun updateDeleteState(deleteState: Boolean, productID: Long)

    @Query("UPDATE product SET deleteState=:deleteState")
    suspend fun falseDeleteState(deleteState: Boolean = false)

    @Query("DELETE FROM product WHERE deleteState=:ifFalse")
    suspend fun deleteItem(ifFalse: Boolean = true)
}
