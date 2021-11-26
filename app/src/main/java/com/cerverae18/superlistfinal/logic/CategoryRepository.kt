package com.cerverae18.superlistfinal.logic

import androidx.annotation.WorkerThread
import com.cerverae18.superlistfinal.logic.entities.Category
import kotlinx.coroutines.flow.Flow

/**
 * A CategoryRepository Class
 *
 * This is class that servers as a single source of truth to all the Category Data from the database
 * @param categoryDao is a CategoryDao that represents the Dao from which the methods will call functions from
 *
 * @property categoryDao represents the Dao from which the methods will call functions from
 * @property allCategories represents a Flow of a list of all the Categories called from the query from the Dao getAll method
 *
 *
 */

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories : Flow<List<Category>> = categoryDao.getAll()

    /**
     *  This a suspend method that calls the Dao's query insert function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param category is a Category to be added to the database via the Dao's query
     *
     */
    @WorkerThread
    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    /**
     *  This a suspend method that calls the Dao's query delete function and explicitly states that it should only
     *  be called from a WorkerThread and inside a coroutine
     *  @param category is a Category to be deleted to the database via the Dao's query
     *
     */
    @WorkerThread
    suspend fun delete(category: Category) {
        categoryDao.delete(category)
    }
}
