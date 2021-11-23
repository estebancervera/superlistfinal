package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.Product
import kotlinx.coroutines.flow.Flow

import kotlin.collections.List


@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Insert
    suspend fun insert(product: Product)

    @Delete
   suspend fun delete(product: Product)
}