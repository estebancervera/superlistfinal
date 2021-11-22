package com.cerverae18.superlistfinal.logic

import androidx.annotation.WorkerThread
import com.cerverae18.superlistfinal.logic.entities.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories : Flow<List<Category>> = categoryDao.getAll()

    @WorkerThread
    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    @WorkerThread
    suspend fun delete(category: Category) {
        categoryDao.delete(category)
    }
}
