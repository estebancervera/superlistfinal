package com.cerverae18.superlistfinal.logic

import androidx.annotation.WorkerThread
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import com.cerverae18.superlistfinal.logic.entities.relations.ListWithProducts
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlinx.coroutines.flow.Flow


class ProductListRepository(private val productListDao: ProductListDao) {


    fun getProductsFromList(id: String): Flow<List<ListWithProducts>> = productListDao.getAllFromList(id)

    @WorkerThread
    suspend fun update(id: String, checked: Boolean) {
        productListDao.update(id, checked)
    }

    @WorkerThread
    suspend fun insert(product: ProductListCrossRef) {
        productListDao.insert(product)
    }

    @WorkerThread
    suspend fun delete(product: ProductListCrossRef) {
        productListDao.delete(product)
    }

}