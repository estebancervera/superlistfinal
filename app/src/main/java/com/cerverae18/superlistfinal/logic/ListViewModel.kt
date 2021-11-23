package com.cerverae18.superlistfinal.logic

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Job

import kotlinx.coroutines.launch

class ListViewModel(private val repository: ListRepository): ViewModel() {
    val allLists : LiveData<List<com.cerverae18.superlistfinal.logic.entities.List>> = repository.allLists.asLiveData()


    fun insert(list: com.cerverae18.superlistfinal.logic.entities.List)= viewModelScope.launch {
        repository.insert(list)
    }

//    fun getById(id: String): List = viewModelScope.launch {
//        return repository.getById(id)
//    }

    fun delete(list: com.cerverae18.superlistfinal.logic.entities.List) = viewModelScope.launch {
        repository.delete(list)
    }

    fun delete(id: String) = viewModelScope.launch {
        repository.deleteById(id)
    }
}


class ListViewModelFactory(private val repository: ListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}