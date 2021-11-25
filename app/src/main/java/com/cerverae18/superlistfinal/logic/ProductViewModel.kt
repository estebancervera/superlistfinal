package com.cerverae18.superlistfinal.logic


import androidx.lifecycle.*
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlin.collections.List


/**
 * A ProductViewModel extends from ViewModel
 *
 * This is class that extends from ViewModel and that creates a ProductViewModel based on a
 * ProductRepository
 * @param repository is a ProductRepository that allows the ViewModel to implement the code from the Dao
 * @property repository is a ProductRepository that allows the ViewModel to implement the code from the Dao
 *
 * @property allProducts represents a list products as LiveData allowing it to be observed.
 * @property allProductsWithCategories represents a list products with category data as LiveData allowing it to be observed.
 */


class ProductViewModel(private val repository: ProductRepository): ViewModel() {

    val allProducts: LiveData<List<Product>> = repository.allProducts.asLiveData()
    val allProductsWithCategories :  LiveData<List<ProductWithCategory>> = repository.allProductsWithCategories.asLiveData()

    /**
     *  This method calls the repository insert function in a new coroutine
     *  @param product is a Product to be added to the database via the repository's insert method
     *  @return returns a Job as a reference to the coroutine
     */

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    /**
     *  This method calls the repository delete function in a new coroutine
     *  @param product is a Product to be deleted from the database via the repository's delete method
     *  @return returns a Job as a reference to the coroutine
     */
    fun delete(product: Product) = viewModelScope.launch {
        repository.delete(product)
    }

    /**
     *  This method calls the repository delete function in a new coroutine
     *  @param id is an Int representing the productId from the product to be deleted from the database via the repository's delete method
     *  @return returns a Job as a reference to the coroutine
     */

    fun delete(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }
}
/**
 * A ProductViewModelFactory implements ViewModelProvider.Factory
 *
 * This is class that implements from ViewModelProvider.Factory that allows the creation ProductViewModels
 *
 * @param repository is a ProductRepository that serves to the creation of a new ProductViewModel
 *
 * @property repository is a ProductRepository that serves to the creation of a new ProductViewModel
 */
class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
