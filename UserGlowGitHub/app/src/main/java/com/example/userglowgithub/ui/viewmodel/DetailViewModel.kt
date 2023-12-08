package com.example.userglowgithub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.userglowgithub.ui.datafavorit.Favorite
import com.example.userglowgithub.ui.response.GitHubUserDetailResponse
import com.example.userglowgithub.ui.response.GlowFollowersResponse
import com.example.userglowgithub.ui.result.FavoriteRepository
import com.example.userglowgithub.ui.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(selectedUser: String, private var favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()

    private val _user = MutableLiveData<GitHubUserDetailResponse>()
    val user: LiveData<GitHubUserDetailResponse> = _user

    private val _glowFollowers = MutableLiveData<List<GlowFollowersResponse>>()
    val glowFollowers: LiveData<List<GlowFollowersResponse>> = _glowFollowers

    private val _glowFollowing = MutableLiveData<List<GlowFollowersResponse>>()
    val glowFollowing: LiveData<List<GlowFollowersResponse>> = _glowFollowing

    private val _isLoadingFollGlow = MutableLiveData<Boolean>()

    init {
        getFollowers(selectedUser)
        getFollowing(selectedUser)
        getDetailDataUser(selectedUser)
    }
    fun getFavoriteUser() = favoriteRepository.getAllFavorites()

    fun addToFavorites(username: String, avatarUrl: String?) {
        val favorite = Favorite(username, avatarUrl)
        favoriteRepository.insertFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) {
        favoriteRepository.deleteFavorite(favorite)

    }
    private fun getFollowers(username: String) {
        _isLoadingFollGlow.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<GlowFollowersResponse>> {
            override fun onResponse(
                call: Call<List<GlowFollowersResponse>>,
                response: Response<List<GlowFollowersResponse>>
            ) {
                _isLoadingFollGlow.value = false
                if (response.isSuccessful) {
                    _glowFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GlowFollowersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")

            }
        })

    }

    private fun getFollowing(username: String) {
        _isLoadingFollGlow.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<GlowFollowersResponse>> {
            override fun onResponse(
                call: Call<List<GlowFollowersResponse>>,
                response: Response<List<GlowFollowersResponse>>
            ) {
                _isLoadingFollGlow.value = false
                if (response.isSuccessful) {
                    _glowFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GlowFollowersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")

            }
        })

    }


    fun getDetailDataUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailDataUser(username)
        client.enqueue(object : Callback<GitHubUserDetailResponse> {
            override fun onResponse(
                call: Call<GitHubUserDetailResponse>,
                response: Response<GitHubUserDetailResponse>
            ) {
                _isLoadingFollGlow.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubUserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")

            }
        })

    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}
















