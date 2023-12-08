package com.example.userglowgithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userglowgithub.databinding.UserListBinding
import com.example.userglowgithub.ui.response.ItemsItem

class ListUserAdapter(val onClick: (String, String) -> Unit) : ListAdapter<ItemsItem, ListUserAdapter.MyViewHolder>(
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
            onClick(list.login, list.avatarUrl)
        }
    }

    class MyViewHolder(val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list : ItemsItem){
            Glide.with(binding.root.context)
                .load(list.avatarUrl).into(binding.foto)
            binding.tvUser.text = list.login

        }
    }


    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
