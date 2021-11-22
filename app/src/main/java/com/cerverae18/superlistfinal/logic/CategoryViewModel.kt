package com.cerverae18.superlistfinal.logic

import androidx.lifecycle.*
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository): ViewModel() {

    val allCategories: LiveData<List<Category>> = repository.allCategories.asLiveData()

    fun insert(category: Category) = viewModelScope.launch {
        repository.insert(category)
    }
    fun delete(category: Category) = viewModelScope.launch {
        repository.delete(category)
    }
}

class CategoryViewModelFactory(private val repository: CategoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
