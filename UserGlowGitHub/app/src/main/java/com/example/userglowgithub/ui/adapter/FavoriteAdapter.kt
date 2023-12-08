package com.example.userglowgithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userglowgithub.databinding.UserListBinding
import com.example.userglowgithub.ui.datafavorit.Favorite

class FavoriteAdapter(val onClick: (String, String) -> Unit) : ListAdapter<Favorite, FavoriteAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)

        holder.binding.root.setOnClickListener {
            onClick(favorite.username, favorite.avatarUrl!!)
        }
    }

    inner class MyViewHolder(val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            Glide.with(binding.foto)
                .load(favorite.avatarUrl)
                .into(binding.foto)
            binding.tvUser.text = favorite.username
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}




