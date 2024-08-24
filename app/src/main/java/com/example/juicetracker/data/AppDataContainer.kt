package com.example.juicetracker.data

import android.content.Context

/**
 * [AppContainer] implementation that provides instance of [RoomProductRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ProductRepository]
     */
    override val productRepository: ProductRepository by lazy {
        RoomProductRepository(AppDatabase.getDatabase(context).juiceDao())
    }
}
