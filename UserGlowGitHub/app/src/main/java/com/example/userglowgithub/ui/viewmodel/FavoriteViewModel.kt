package com.example.userglowgithub.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.userglowgithub.ui.result.FavoriteRepository

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    fun getFavoriteUser() = repository.getAllFavorites()


}