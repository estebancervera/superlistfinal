package com.cerverae18.superlistfinal.logic

import androidx.annotation.WorkerThread
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlinx.coroutines.flow.Flow
/**
 * A ListRepository Class
 *
 * This is class that servers as a single source of truth to all the List Data from the database
 * @param listDao is a ListDao that represents the Dao from which the methods will call functions from
 *
 * @property listDao represents the Dao from which the methods will call functions from
 * @property allLists represents a Flow of a list of all the List called from the query from the Dao getAll method
 *
 *
 */
class ListRepository(private val listDao: ListDao){

    val allLists : Flow<List<com.cerverae18.superlistfinal.logic.entities.List>> = listDao.getAll()

    /**
     *  This a  method that calls the Dao's query function to get the List assigned to a list
     *  @param id is an String representing the listId of the List to be fetch to the database via the Dao's query
     *  @returns a Flow of lists of ListWithProducts
     */
    fun listById(id: String) : Flow<com.cerverae18.superlistfinal.logic.entities.List> = listDao.getListById(id)

    /**
     *  This a suspend method that calls the Dao's query insert function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param list is a List to be added to the database via the Dao's query
     *
     */
    @WorkerThread
    suspend fun insert(list: com.cerverae18.superlistfinal.logic.entities.List): Long {
       return listDao.insert(list)
    }

    /**
     *  This a suspend method that calls the Dao's query delete function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param list is a List to be deleted to the database via the Dao's query
     *
     */
    @WorkerThread
    suspend fun delete(list: com.cerverae18.superlistfinal.logic.entities.List) {
        listDao.delete(list)
    }

    /**
     *  This a suspend method that calls the Dao's query delete function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param id is an String representing the ID from the List to be deleted by the Dao's query
     *
     */
    @WorkerThread
    suspend fun deleteById(id: String) {
        listDao.deleteById(id)
    }
}