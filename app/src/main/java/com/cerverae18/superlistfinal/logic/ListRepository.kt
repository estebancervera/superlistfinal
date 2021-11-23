package com.cerverae18.superlistfinal.logic

import androidx.annotation.WorkerThread
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlinx.coroutines.flow.Flow

class ListRepository(val listDao: ListDao){

    val allLists : Flow<List<com.cerverae18.superlistfinal.logic.entities.List>> = listDao.getAll()

    fun listById(id: String) : Flow<com.cerverae18.superlistfinal.logic.entities.List> = listDao.getListById(id)


    @WorkerThread
    suspend fun insert(list: com.cerverae18.superlistfinal.logic.entities.List): Long {
       return listDao.insert(list)
    }

    @WorkerThread
    suspend fun delete(list: com.cerverae18.superlistfinal.logic.entities.List) {
        listDao.delete(list)
    }

    @WorkerThread
    suspend fun deleteById(id: String) {
        listDao.deleteById(id)
    }
}