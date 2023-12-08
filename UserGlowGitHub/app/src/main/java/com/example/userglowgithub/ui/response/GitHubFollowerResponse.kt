package com.example.userglowgithub.ui.response

import com.google.gson.annotations.SerializedName

data class GlowFollowersResponse(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

)
