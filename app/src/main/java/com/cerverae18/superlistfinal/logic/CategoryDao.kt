package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product
import kotlinx.coroutines.flow.Flow
/**
 * An Product Dao Interface
 *
 * This interface is marked as @DAO and it defines the methods and respective queries to the database that the repository can make.
 *
 */
@Dao
interface CategoryDao {
    /**
     *  This is a method to be called in the repository with a query attached to fetch all Category from the database
     *  @returns a FLOW list of Category
     */
    @Query("SELECT * FROM category")
    fun getAll(): Flow<List<Category>>

    /**
     *  This is a suspend method to be accessed in the repository marked as @insert to insert category into the database
     *  @param category is a Category to be inserted
     *   @returns Unit
     */
    @Insert
    suspend fun insert(category: Category)

    /**
     *  This is a suspend method to be accessed in the repository marked as @delete to delete a Category from the database
     *  @param category is a Category to be deleted
     *   @returns Unit
     */
    @Delete
    suspend fun delete(category: Category)

    /**
     *  This is an  suspend method to be accessed in the repository with a query to delete all categories from the database
     *
     *   @returns Unit
     */
    @Query("DELETE FROM category")
    suspend fun deleteAll()
}