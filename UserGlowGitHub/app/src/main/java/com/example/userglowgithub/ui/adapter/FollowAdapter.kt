package com.example.userglowgithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userglowgithub.databinding.UserListBinding
import com.example.userglowgithub.ui.response.GlowFollowersResponse


class FollowAdapter(val onClick: (String) -> Unit) :
    ListAdapter<GlowFollowersResponse, FollowAdapter.MyViewHolder>(
        DIFF_CALLBACK

    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
        holder.binding.root.setOnClickListener{
            onClick(list.login)
        }
    }

    class MyViewHolder(val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list : GlowFollowersResponse){
            Glide.with(binding.root.context)
                .load(list.avatarUrl).into(binding.foto)
            binding.tvUser.text = list.login

        }
    }


    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GlowFollowersResponse>() {
            override fun areItemsTheSame(oldItem: GlowFollowersResponse, newItem: GlowFollowersResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GlowFollowersResponse, newItem: GlowFollowersResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}
