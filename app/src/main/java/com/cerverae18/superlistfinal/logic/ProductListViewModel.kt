package com.cerverae18.superlistfinal.logic

import androidx.lifecycle.*
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlinx.coroutines.launch

class ProductListViewController {
}
class ProductListViewModel(private val repository: ProductListRepository): ViewModel() {

    fun insert(product: ProductListCrossRef) = viewModelScope.launch {
        repository.insert(product)
    }
    fun delete(product: ProductListCrossRef) = viewModelScope.launch {
        repository.delete(product)
    }

}

class ProductListViewModelFactory(private val repository: ProductListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

