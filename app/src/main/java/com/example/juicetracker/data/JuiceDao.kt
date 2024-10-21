package com.example.juicetracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    @Query("SELECT checkState FROM product")
    fun getCheckList(): Flow<List<Boolean>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Query("SELECT * FROM product WHERE name = :name")
    suspend fun getItemByName(name: String): Product?

    suspend fun insertItemIfNotExists(product: Product): Boolean {
        if (getItemByName(product.name) == null) {
            return true
        } else {
            return false
            // Handle the case where the item already exists (e.g., show a message)
        }
    }

    @Delete
    suspend fun delete(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("UPDATE product SET minPrice =:minPrice, maxPrice =:maxPrice WHERE checkState = True")
    suspend fun editPrice(minPrice: Float, maxPrice: Float)

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
