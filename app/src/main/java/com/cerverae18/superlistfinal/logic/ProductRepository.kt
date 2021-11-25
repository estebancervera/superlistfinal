package com.cerverae18.superlistfinal.logic

import androidx.annotation.WorkerThread
import com.cerverae18.superlistfinal.logic.ProductDao
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List


/**
 * A ProductRepository Class
 *
 * This is class that servers as a single source of truth to all the Product Data from the database
 * @param productDao is a ProductDao that represents the Dao from which the methods will call functions from
 *
 * @property productDao represents the Dao from which the methods will call functions from
 * @property allProducts represents a Flow of a list of all the Products called from the query from the Dao getAll method
 * @property allProductsWithCategories represents a Flow of a list of all the ProductWithCategory called from the query from the Dao getAllWithCategory method
 *
 */

class ProductRepository(private val productDao: ProductDao) {

    val allProducts : Flow<List<Product>> = productDao.getAll()

    val allProductsWithCategories : Flow<List<ProductWithCategory>> = productDao.getAllWithCategory()

    /**
     *  This a suspend method that calls the Dao's query insert function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param product is a Product to be added to the database via the Dao's query
     *
     */

    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    /**
     *  This a suspend method that calls the Dao's query delete function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param product is a Product to be deleted to the database via the Dao's query
     *
     */

    @WorkerThread
    suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    /**
     *  This a suspend method that calls the Dao's query delete function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param id is an Int representing the ID from the product to be deleted by the Dao's query
     *
     */

    @WorkerThread
    suspend fun deleteById(id: Int) {
        productDao.deleteById(id)
    }
}