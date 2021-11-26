package com.cerverae18.superlistfinal.logic

import androidx.lifecycle.*
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import com.cerverae18.superlistfinal.logic.entities.relations.ProductFromList
import kotlinx.coroutines.launch

/**
 * A ProductListViewModel extends from ViewModel
 *
 * This is class that extends from ViewModel and that creates a ProductListViewModel based on a
 * ProductListRepository
 * @param repository is a ProductListRepository that allows the ViewModel to implement the code from the Dao
 * @property repository is a ProductListRepository that allows the ViewModel to implement the code from the Dao
 *
 */

class ProductListViewModel(private val repository: ProductListRepository): ViewModel() {

    /**
     *  This method calls the repository getProductsFromList function to get a LiveData of the products from a List
     *  @param id is a String representing the listId from a list in the database
     *  @return a LiveData of a list of ListWithProducts
     */
    fun productsFromList(id: String): LiveData<List<ProductFromList>> = repository.getProductsFromList(id).asLiveData()

    /**
     *  This method calls the repository update function in a new coroutine
     *  @param id is a String representing the listId from a list in the database
     *  @param checked is a Boolean representing wether the item has been checked or not
     *  @return returns a Job as a reference to the coroutine
     */
    fun update(id: String, checked: Boolean) = viewModelScope.launch {
        repository.update(id, checked)
    }

    /**
     *  This method calls the repository insert function in a new coroutine
     *  @param product is a ProductListCrossRef to be added to the database via the repository's insert method
     *  @return returns a Job as a reference to the coroutine
     */
    fun insert(product: ProductListCrossRef) = viewModelScope.launch {
        repository.insert(product)
    }
    /**
     *  This method calls the repository delete function in a new coroutine
     *  @param product is a ProductListCrossRef to be deleted from the database via the repository's delete method
     *  @return returns a Job as a reference to the coroutine
     */
    fun delete(product: ProductListCrossRef) = viewModelScope.launch {
        repository.delete(product)
    }

}

/**
 * A ProductListViewModelFactory implements ViewModelProvider.Factory
 *
 * This is class that implements from ViewModelProvider.Factory that allows the creation ProductListViewModel
 *
 * @param repository is a ProductListRepository that serves to the creation of a new ProductListViewModel
 *
 * @property repository is a ProductListRepository that serves to the creation of a new ProductListViewModel
 */
class ProductListViewModelFactory(private val repository: ProductListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

