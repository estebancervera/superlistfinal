package com.cerverae18.superlistfinal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.collections.List

//class ProductsViewModel {
//
//    val allSongs: LiveData<List<Song>> = repository.allSongs.asLiveData()
//
//    fun insert(song: Song) = viewModelScope.launch {
//        repository.insert(song)
//    }
//}
//
//class SongViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return SongViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
