package com.example.userglowgithub.ui.result

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.userglowgithub.ui.datafavorit.Favorite
import com.example.userglowgithub.ui.datafavorit.FavoriteDao
import com.example.userglowgithub.ui.datafavorit.FavoriteRoomDatabase

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {
    fun getAllFavorites(): LiveData<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }

    fun insertFavorite(favorite: Favorite) {
        appExecutors.diskIO.execute {
            favoriteDao.insertDataFavorite(favorite)
        }

    }

    fun deleteFavorite(favorite: Favorite) {
        appExecutors.diskIO.execute {
            favoriteDao.deleteDataFavorite(favorite)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null

        fun getInstance(
            context: Context,
        ): FavoriteRepository =
            instance ?: synchronized(FavoriteRepository::class.java) {
                if (instance == null) {
                    val database = FavoriteRoomDatabase.getDatabase(context)
                    instance = FavoriteRepository(database.favoriteDao(), AppExecutors())
                }
                return instance as FavoriteRepository
            }
    }
}
