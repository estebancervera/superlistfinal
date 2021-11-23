package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlinx.coroutines.flow.Flow

import kotlin.collections.List


@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT product.productId as id , product.name , category.name as category FROM product INNER JOIN category ON category.categoryId = product.category")
    fun getAllWithCategory(): Flow<List<ProductWithCategory>>

    @Insert
    suspend fun insert(product: Product)

    @Delete
   suspend fun delete(product: Product)

    @Query("DELETE FROM product WHERE productId = :id")
    suspend fun deleteById(id: Int)
}