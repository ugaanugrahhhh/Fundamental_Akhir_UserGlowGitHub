package com.example.userglowgithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userglowgithub.R
import com.example.userglowgithub.databinding.ActivityMainBinding
import com.example.userglowgithub.ui.adapter.ListUserAdapter
import com.example.userglowgithub.ui.response.GitHubSearchResponse
import com.example.userglowgithub.ui.retrofit.ApiConfig
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isNightModeEnabled = sharedPreferences.getBoolean("night_mode", false)
        if (isNightModeEnabled){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        setContentView(binding.root)

        binding.progressBar3.visibility = View.GONE
        val fabFavorite = findViewById<FloatingActionButton>(R.id.fabFavorite)
        fabFavorite.setOnClickListener {
            val intent = Intent(this, FavoritePageActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.RvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.RvUser.addItemDecoration(itemDecoration)

        val adapter = ListUserAdapter { username,url ->
            val intent = Intent(this@MainActivity, DetailUsersActivity::class.java)
            intent.putExtra(DetailUsersActivity.USER, username)
            intent.putExtra(DetailUsersActivity.URL, url)
            startActivity(intent)
        }

        binding.RvUser.adapter = adapter

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()

                    findSearch(searchView.text.toString())

                    false
                }
        }
        val toolbar = findViewById<MaterialToolbar>(R.id.DarkMode)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@MainActivity, DarkModeActivity::class.java)
            startActivity(intent)
        }
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_dark_mode -> {
                    val intent = Intent(this, DarkModeActivity::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun findSearch(findUser: String) {
        val client = ApiConfig.getApiService().getGitHubSearch(findUser)
        binding.progressBar3.visibility = View.VISIBLE
        client.enqueue(object : Callback<GitHubSearchResponse> {
            override fun onResponse(
                call: Call<GitHubSearchResponse>,
                response: Response<GitHubSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val accounts = responseBody.items
                        val adapter = binding.RvUser.adapter as ListUserAdapter
                        adapter.submitList(accounts)

                        binding.progressBar3.visibility = View.GONE

                        Log.d("TAG Success", "Response body: $responseBody")
                    }
                } else {
                    binding.progressBar3.visibility = View.GONE
                    Log.e("TAG", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubSearchResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
                binding.progressBar3.visibility = View.GONE
            }
        })
    }
}
