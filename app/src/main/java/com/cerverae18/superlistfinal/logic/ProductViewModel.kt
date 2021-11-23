package com.cerverae18.superlistfinal.logic


import androidx.lifecycle.*
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlin.collections.List

class ProductViewModel(private val repository: ProductRepository): ViewModel() {

    val allProducts: LiveData<List<Product>> = repository.allProducts.asLiveData()
    val allProductsWithCategories :  LiveData<List<ProductWithCategory>> = repository.allProductsWithCategories.asLiveData()

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }
    fun delete(product: Product) = viewModelScope.launch {
        repository.delete(product)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }
}

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
