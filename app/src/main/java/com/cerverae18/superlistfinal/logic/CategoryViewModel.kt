package com.cerverae18.superlistfinal.logic

import androidx.lifecycle.*
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product
import kotlinx.coroutines.launch

/**
 * A CategoryViewModel extends from ViewModel
 *
 * This is class that extends from ViewModel and that creates a CategoryViewModel based on a
 * CategoryRepository
 * @param repository is a CategoryRepository that allows the ViewModel to implement the code from the Dao
 * @property repository is a CategoryRepository that allows the ViewModel to implement the code from the Dao
 *@property allCategories is a LiveData of a list of Category objects allowing it to be observed.
 */
class CategoryViewModel(private val repository: CategoryRepository): ViewModel() {

    val allCategories: LiveData<List<Category>> = repository.allCategories.asLiveData()

    /**
     *  This method calls the repository insert function in a new coroutine
     *  @param category is a Category to be added to the database via the repository's insert method
     *  @return returns a Job as a reference to the coroutine
     */
    fun insert(category: Category) = viewModelScope.launch {
        repository.insert(category)
    }
    /**
     *  This method calls the repository delete function in a new coroutine
     *  @param category is a Category to be deleted from the database via the repository's delete method
     *  @return returns a Job as a reference to the coroutine
     */
    fun delete(category: Category) = viewModelScope.launch {
        repository.delete(category)
    }
}
/**
 * A CategoryViewModelFactory implements ViewModelProvider.Factory
 *
 * This is class that implements from ViewModelProvider.Factory that allows the creation CategoryViewModels
 *
 * @param repository is a CategoryRepository that serves to the creation of a new CategoryViewModel
 *
 * @property repository is a CategoryRepository that serves to the creation of a new CategoryViewModel
 */
class CategoryViewModelFactory(private val repository: CategoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
