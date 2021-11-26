package com.cerverae18.superlistfinal.logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cerverae18.superlistfinal.logic.entities.Product
import kotlinx.coroutines.flow.Flow

/**
 * An Product Dao Interface
 *
 * This interface is marked as @DAO and it defines the methods and respective queries to the database that the repository can make.
 *
 */
@Dao
interface ListDao {
    /**
     *  This is a method to be called in the repository with a query attached to fetch all lists from the database
     *  @returns a FLOW list of Lists
     */
    @Query("SELECT * FROM list ORDER BY date ASC")
    fun getAll(): Flow<List<com.cerverae18.superlistfinal.logic.entities.List>>

    /**
     *  This is a method to be called in the repository with a query attached to fetch one lists from the database with the Id provided
     *  @param id is a String representing the listId of the list to be fetched
     *  @returns a FLOW  List
     */
    @Query("SELECT * FROM list WHERE listId = :id")
    fun getListById(id: String) : Flow<com.cerverae18.superlistfinal.logic.entities.List>

    /**
     *  This is a suspend method to be accessed in the repository marked as @insert to insert lists into the database
     *  @param list is a List to be inserted
     *   @returns Unit
     */
    @Insert
    suspend fun insert(list: com.cerverae18.superlistfinal.logic.entities.List) : Long
    /**
     *  This is a suspend method to be accessed in the repository marked as @delete to delete a lists from the database
     *  @param list is a List to be deleted
     *   @returns Unit
     */
    @Delete
    suspend fun delete(list: com.cerverae18.superlistfinal.logic.entities.List)

    /**
     *  This is an  suspend method to be accessed in the repository with a query to delete a lists by its ID
     *  @param id is an String representing the listId from the List to be deleted
     *   @returns Unit
     */
    @Query("DELETE FROM list WHERE listId = :id")
    suspend fun deleteById(id: String)
}

