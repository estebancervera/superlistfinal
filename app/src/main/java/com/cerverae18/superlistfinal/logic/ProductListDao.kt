package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import com.cerverae18.superlistfinal.logic.entities.relations.ProductFromList
import kotlinx.coroutines.flow.Flow

/**
 * An ProductList Dao Interface
 *
 * This interface is marked as @DAO and it defines the methods and respective queries to the database that the repository can make.
 *
 */
@Dao
interface ProductListDao {

    /**
     *  This is an abstract method to be implemented in the repository with a query attached to fetch all ProductListCrossRef associated with a List from the database
     *  @param id is a String representing the listId of the ProductListCrossRef to be fetch
     *  @returns a FLOW list of ListWithProducts
     */
    @Query("SELECT id,  product.name as productName, quantity, category.name as categoryName, checked FROM product_list  INNER JOIN product ON product_list.productId = product.productId INNER JOIN category ON product.category = category.categoryId WHERE listId = :id")
    fun getAllFromList(id: String): Flow<List<ProductFromList>>

    /**
     *  This is an abstract method to be implemented in the repository with a query update a ProductListCrossRef's checked value
     *   @param id is a String representing the listId of the ProductListCrossRef to be edited
     *   @param checked is a Boolean representing wether a ProductListCrossRef is checked or not
     *  @returns Unit
     */
    @Query("UPDATE product_list SET checked=:checked WHERE id = :id")
    suspend fun update(id: String,  checked: Boolean)

    /**
     *  This is an abstract suspend method to be implemented in the repository marked as @insert to insert ProductListCrossRef into the database
     *  @param product is a ProductListCrossRef to be inserted
     *   @returns Unit
     */
    @Insert
    suspend fun insert(product: ProductListCrossRef)
    /**
     *  This is an abstract suspend method to be implemented in the repository marked as @delete to delete a ProductListCrossRef from the database
     *  @param product is a ProductListCrossRef to be deleted
     *   @returns Unit
     */
    @Delete
    suspend fun delete(product: ProductListCrossRef)


}