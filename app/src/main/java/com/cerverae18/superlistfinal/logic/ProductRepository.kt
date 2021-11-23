package com.cerverae18.superlistfinal.logic

import androidx.annotation.WorkerThread
import com.cerverae18.superlistfinal.logic.ProductDao
import com.cerverae18.superlistfinal.logic.entities.Product
<<<<<<< HEAD
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
=======
>>>>>>> master
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List

class ProductRepository(private val productDao: ProductDao) {

    val allProducts : Flow<List<Product>> = productDao.getAll()

<<<<<<< HEAD
    val allProductsWithCategories : Flow<List<ProductWithCategory>> = productDao.getAllWithCategory()

=======
>>>>>>> master
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }
<<<<<<< HEAD

    @WorkerThread
    suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    @WorkerThread
    suspend fun deleteById(id: Int) {
        productDao.deleteById(id)
    }
=======
>>>>>>> master
}