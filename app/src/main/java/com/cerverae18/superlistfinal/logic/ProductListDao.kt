package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductListDao {

    @Insert
    suspend fun insert(product: ProductListCrossRef)

    @Delete
    suspend fun delete(product: ProductListCrossRef)


}