package com.cerverae18.superlistfinal.logic

import androidx.annotation.WorkerThread
import com.cerverae18.superlistfinal.logic.ProductDao
import com.cerverae18.superlistfinal.logic.entities.Product
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List

class ProductRepository(private val productDao: ProductDao) {

    val allProducts : Flow<List<Product>> = productDao.getAll()

    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    @WorkerThread
    suspend fun delete(product: Product) {
        productDao.delete(product)
    }
}