package com.example.withmeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.withmeapp.databinding.LocallistViewBinding
import java.nio.file.Files.size

class LocalAdapter(private val items:MutableList<locallist_data>):
    RecyclerView.Adapter<LocalAdapter.ViewHolder> () {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val binding = LocallistViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)

        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val UserInfo = items[position]
            holder.bind(UserInfo)
        }
        override fun getItemCount(): Int {
            return items.size
        }

        class ViewHolder(private val binding: LocallistViewBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(users: locallist_data) {
                binding.userimage =
                binding.userid.text =
                binding.away.text =
                binding.startLoc.text =
            }
        }
    }
}