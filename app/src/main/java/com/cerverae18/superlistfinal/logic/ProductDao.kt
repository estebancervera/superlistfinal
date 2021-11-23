package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.Product
<<<<<<< HEAD
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
=======
>>>>>>> master
import kotlinx.coroutines.flow.Flow

import kotlin.collections.List


@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

<<<<<<< HEAD
    @Query("SELECT product.productId as id , product.name , category.name as category FROM product INNER JOIN category ON category.categoryId = product.category")
    fun getAllWithCategory(): Flow<List<ProductWithCategory>>

=======
>>>>>>> master
    @Insert
    suspend fun insert(product: Product)

    @Delete
   suspend fun delete(product: Product)
<<<<<<< HEAD

    @Query("DELETE FROM product WHERE productId = :id")
    suspend fun deleteById(id: Int)
=======
>>>>>>> master
}