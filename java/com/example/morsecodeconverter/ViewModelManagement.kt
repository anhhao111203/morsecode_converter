package com.example.morsecodeconverter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.morsecodeconverter.favorites.FavoriteWords

class FavoritesViewModel: ViewModel() {
    val favoriteWords = mutableStateListOf<FavoriteWords>()

    fun addFavorite(word: FavoriteWords) {
        favoriteWords.add(word)
    }
    fun removeFavorite(word: FavoriteWords) {
        favoriteWords.remove(word)
    }
}

class NavigationViewModel: ViewModel() {
    private val _selectedItem = mutableStateOf("Translation")
    
    val selectedItem: State<String> = _selectedItem

    fun selectItem(item: String) {
        _selectedItem.value = item
    }

}