package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.Product

import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory

import kotlinx.coroutines.flow.Flow

import kotlin.collections.List

/**
 * An Product Dao Interface
 *
 * This interface is marked as @DAO and it defines the methods and respective queries to the database that the repository can make.
 *
 */
@Dao
interface ProductDao {


    /**
     *  This is an abstract method to be implemented in the repository with a query attached to fetch all products from the database
     *  @returns a FLOW list of Products
     */
    @Query("SELECT * FROM product")
     fun getAll(): Flow<List<Product>>


    /**
     *  This is an abstract method to be implemented in the repository with a query attached to fetch all products with their categories from the database
     *   @returns a FLOW list of ProductWithCategory
     */
    @Query("SELECT product.productId as id , product.name , category.name as category FROM product INNER JOIN category ON category.categoryId = product.category")
    fun getAllWithCategory(): Flow<List<ProductWithCategory>>

    /**
     *  This is an abstract suspend method to be implemented in the repository marked as @insert to insert products into the database
     *  @param product is a Product to be inserted
     *   @returns Unit
     */
    @Insert
    suspend fun insert(product: Product)

    /**
     *  This is an abstract suspend method to be implemented in the repository marked as @delete to delete a product from the database
     *  @param product is a Product to be deleted
     *   @returns Unit
     */
    @Delete
   suspend fun delete(product: Product)

    /**
     *  This is an abstract suspend method to be implemented in the repository with a query to delete a product by its ID
     *  @param id is an Int representing the productId from the product to be deleted
     *   @returns Unit
     */
    @Query("DELETE FROM product WHERE productId = :id")
    suspend fun deleteById(id: Int)

}