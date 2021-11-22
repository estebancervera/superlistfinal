package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {
    @Query("SELECT * FROM list")
    fun getAll(): Flow<List<com.cerverae18.superlistfinal.logic.entities.List>>

    @Insert
    suspend fun insert(list: com.cerverae18.superlistfinal.logic.entities.List)

    @Delete
    suspend fun delete(list: com.cerverae18.superlistfinal.logic.entities.List)
}

