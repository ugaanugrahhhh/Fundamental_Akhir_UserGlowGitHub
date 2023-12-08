package com.example.userglowgithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userglowgithub.R
import com.example.userglowgithub.databinding.ActivityFavoritePageBinding
import com.example.userglowgithub.ui.adapter.FavoriteAdapter
import com.example.userglowgithub.ui.viewmodel.DetailViewModelFactory
import com.example.userglowgithub.ui.viewmodel.FavoriteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoritePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritePageBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.GONE
        val fabFavorite = findViewById<FloatingActionButton>(R.id.fabFavorite)
        fabFavorite.setOnClickListener {
            val intent = Intent(this, FavoritePageActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this,DetailViewModelFactory.getInstance(this,"")
        )[FavoriteViewModel::class.java]

        adapter = FavoriteAdapter { username,url ->
            val intent = Intent(this@FavoritePageActivity, DetailUsersActivity::class.java)
            intent.putExtra(DetailUsersActivity.USER, username)
            intent.putExtra(DetailUsersActivity.URL, url)
            startActivity(intent)
        }

        binding.recyclerViewFavorite.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFavorite.layoutManager = layoutManager

        viewModel.getFavoriteUser().observe(this) { favorite ->
            Log.d("TAG Like It", "onCreate: $favorite")
            adapter.submitList(favorite)
        }

        binding.fabFavorite.setOnClickListener {
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
