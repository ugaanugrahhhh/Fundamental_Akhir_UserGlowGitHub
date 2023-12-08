package com.example.userglowgithub.ui.datafavorit

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDataFavorite(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Delete
    fun deleteDataFavorite(favorite: Favorite)

}
