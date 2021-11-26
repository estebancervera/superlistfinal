package com.cerverae18.superlistfinal.logic

import androidx.lifecycle.*
import kotlinx.coroutines.launch

/**
 * A ListViewModel extends from ViewModel
 *
 * This is class that extends from ViewModel and that creates a ListViewModel based on a
 * ListRepository
 * @param repository is a ListRepository that allows the ViewModel to implement the code from the Dao
 * @property repository is a ListRepository that allows the ViewModel to implement the code from the Dao
 * @property allLists represents a list of objects List as LiveData allowing it to be observed.
 *
 */

class ListViewModel(private val repository: ListRepository): ViewModel() {
    val allLists : LiveData<List<com.cerverae18.superlistfinal.logic.entities.List>> = repository.allLists.asLiveData()

    /**
     *  This method calls the repository listById function to get a LiveData of a single List searches by Id
     *  @param id is a String representing the listId from a list in the database
     *  @return a LiveData of a single List
     */
    fun getListById(id: String) : LiveData<com.cerverae18.superlistfinal.logic.entities.List> = repository.listById(id).asLiveData()


    /**
     *  This method calls the repository insert function in a new coroutine
     *  @param list is a List to be added to the database via the repository's insert method
     *  @return returns a Job as a reference to the coroutine
     */
    fun insert(list: com.cerverae18.superlistfinal.logic.entities.List)= viewModelScope.launch {
        repository.insert(list)
    }

    /**
     *  This method calls the repository delete function in a new coroutine
     *  @param list is a List to be deleted from the database via the repository's delete method
     *  @return returns a Job as a reference to the coroutine
     */

    fun delete(list: com.cerverae18.superlistfinal.logic.entities.List) = viewModelScope.launch {
        repository.delete(list)
    }

    /**
     *  This method calls the repository delete function in a new coroutine
     *  @param id is an String representing the listId from the product to be deleted from the database via the repository's delete method
     *  @return returns a Job as a reference to the coroutine
     */

    fun delete(id: String) = viewModelScope.launch {
        repository.deleteById(id)
    }
}
/**
 * A ListViewModelFactory implements ViewModelProvider.Factory
 *
 * This is class that implements from ViewModelProvider.Factory that allows the creation ListViewModels
 *
 * @param repository is a ListRepository that serves to the creation of a new ListViewModel
 *
 * @property repository is a ListRepository that serves to the creation of a new ListViewModel
 */

class ListViewModelFactory(private val repository: ListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}