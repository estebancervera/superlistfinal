package com.cerverae18.superlistfinal.logic

import androidx.annotation.WorkerThread
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import com.cerverae18.superlistfinal.logic.entities.relations.ListWithProducts
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlinx.coroutines.flow.Flow

/**
 * A ProductListRepository Class
 *
 * This is class that servers as a single source of truth to all the ProductListCrossRef Data from the database
 * @param productListDao is a ProductListDao that represents the Dao from which the methods will call functions from
 *
 * @property productListDao represents the Dao from which the methods will call functions from

 */
class ProductListRepository(private val productListDao: ProductListDao) {

    /**
     *  This a  method that calls the Dao's query function to get all the ProductListCrossRef assigned to a list
     *  @param id is an String representing the listId of the ProductListCrossRefs to be fetch to the database via the Dao's query
     *  @returns a Flow of lists of ListWithProducts
     */
    fun getProductsFromList(id: String): Flow<List<ListWithProducts>> = productListDao.getAllFromList(id)

    /**
     *  This a suspend method that calls the Dao's query update function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param id is an String representing the listId of a ProductListCrossRef to be updated to the database via the Dao's query
     *  @param checked is a Boolean representing wether a ProductListCrossRef is checked or not, to be updated to the database via the Dao's query
     */
    @WorkerThread
    suspend fun update(id: String, checked: Boolean) {
        productListDao.update(id, checked)
    }

    /**
     *  This a suspend method that calls the Dao's query insert function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param product is a ProductListCrossRef to be added to the database via the Dao's query
     *
     */
    @WorkerThread
    suspend fun insert(product: ProductListCrossRef) {
        productListDao.insert(product)
    }

    /**
     *  This a suspend method that calls the Dao's query delete function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param product is a ProductListCrossRef to be deleted to the database via the Dao's query
     *
     */
    @WorkerThread
    suspend fun delete(product: ProductListCrossRef) {
        productListDao.delete(product)
    }

}