package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import com.cerverae18.superlistfinal.logic.entities.relations.ListWithProducts
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductListDao {

    @Query("SELECT id,  product.name as productName, quantity, category.name as categoryName, checked FROM product_list  INNER JOIN product ON product_list.productId = product.productId INNER JOIN category ON product.category = category.categoryId WHERE listId = :id")
    fun getAllFromList(id: String): Flow<List<ListWithProducts>>


    @Query("UPDATE product_list SET checked=:checked WHERE id = :id")
    suspend fun update(id: String,  checked: Boolean)


    @Insert
    suspend fun insert(product: ProductListCrossRef)

    @Delete
    suspend fun delete(product: ProductListCrossRef)


}