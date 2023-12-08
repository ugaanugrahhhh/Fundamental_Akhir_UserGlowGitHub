package com.example.userglowgithub.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userglowgithub.ui.result.FavoriteRepository

class DetailViewModelFactory private constructor(

    private val selectedUser: String,
    private val favoriteRepository : FavoriteRepository

) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: DetailViewModelFactory? = null

        fun getInstance(
            context: Context,
            selectedUser: String,
        ): DetailViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailViewModelFactory(
                    selectedUser, FavoriteRepository.getInstance(context )
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(DetailViewModel::class.java)->{
                DetailViewModel(selectedUser, favoriteRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java)->{
                FavoriteViewModel( favoriteRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}
