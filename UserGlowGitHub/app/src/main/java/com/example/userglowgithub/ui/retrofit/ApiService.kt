package com.example.userglowgithub.ui.retrofit

import com.example.userglowgithub.ui.response.GitHubSearchResponse
import com.example.userglowgithub.ui.response.GitHubUserDetailResponse
import com.example.userglowgithub.ui.response.GlowFollowersResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getGitHubSearch(
    @Query("q") id: String
    ): Call<GitHubSearchResponse>

    @GET("users/{username}")
    fun getDetailDataUser(
    @Path("username") username : String
    ): Call<GitHubUserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username : String
    ): Call<List<GlowFollowersResponse>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username : String
    ): Call<List<GlowFollowersResponse>>

}


