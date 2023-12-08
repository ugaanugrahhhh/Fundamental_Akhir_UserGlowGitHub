@file:Suppress("SameParameterValue")
package com.example.userglowgithub.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.userglowgithub.R
import com.example.userglowgithub.databinding.ActivityDetailUsersBinding
import com.example.userglowgithub.ui.adapter.SectionsPagerAdapter
import com.example.userglowgithub.ui.datafavorit.Favorite
import com.example.userglowgithub.ui.response.GitHubUserDetailResponse
import com.example.userglowgithub.ui.viewmodel.DetailViewModel
import com.example.userglowgithub.ui.viewmodel.DetailViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUsersBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val choose: String = intent.getStringExtra(USER)!!
        detailViewModel = ViewModelProvider(this, DetailViewModelFactory.getInstance(this, choose))[DetailViewModel::class.java]
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val detail = intent.getStringExtra(USER).toString()
        val url = intent.getStringExtra(URL).toString()

        detailViewModel.user.observe(this) { detailUser ->
           getDetailDataUser(detailUser)
            showLoading(false)
        }
        detailViewModel.getDetailDataUser(detail)

        val fabFavorite: FloatingActionButton = binding.fabFavorite
        detailViewModel.getFavoriteUser().observe(this) { listFavorite ->
            val avatarUrl = url
            binding.foto
            var favoriteStatus = false

            for (favorite in listFavorite) {
                if (favorite.username == detail) {
                    Glide.with(this).load(avatarUrl).into(binding.foto)
                    favoriteStatus = true
                    break

                }else{
                    favoriteStatus = false
                }
            }

            fabFavorite.setImageResource(if (favoriteStatus) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
            fabFavorite.setOnClickListener {
                if (!favoriteStatus) {
                    fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    detailViewModel.addToFavorites(detail, avatarUrl)
                    showLoading(false)
                } else {
                    fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    val favorite = Favorite(detail, avatarUrl)
                    detailViewModel.deleteFavorite(favorite)
                    showLoading(false)
                }
                favoriteStatus =!favoriteStatus
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getDetailDataUser(detail: GitHubUserDetailResponse) {
        Glide.with(this).load(detail.avatarUrl).into(binding.foto)
        binding.tvUser.text = detail.name
        binding.tvUsername.text = detail.login
        binding.tvFollowers.text = "${detail.followers} Followers"
        binding.tvFollowing.text = "${detail.following} Following"
        showLoading(true)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val USER = "user"
        const val URL = "url"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}
