package com.example.withmeapp

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.withmeapp.databinding.LocallistViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.util.*
import kotlin.collections.ArrayList

class LocalAdapter(private val items: MutableList<Locallist_data>) :
     RecyclerView.Adapter<LocalAdapter.ViewHolder>() {

//    init {  // items의 문서를 불러온 뒤 locallist_data으로 변환해 ArrayList에 담음
//        FirebaseDatabase.getInstance().reference.child("UserList")
//            .addValueEventListener(object : ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    // ArrayList 비워줌
//                    items.clear()
//
//                    for (data in snapshot.children) {
//                        val item = data.getValue<Locallist_data>()
//                        items.add(item!!)
//                    }
//
//                    notifyDataSetChanged()
//                }
//            })

//            val postListener = object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    // Get Post object and use the values to update the UI
//                    val items = dataSnapshot.getValue<Locallist_data>()
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // Getting Post failed, log a message
//                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//                }
//            }
//            database.addValueEventListener(postListener)

//    }

    inner class ViewHolder( val binding: LocallistViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(locallistData: Locallist_data) {


            binding.userID.text = locallistData.userID
            binding.within.text = locallistData.within
            binding.startLoc.text = locallistData.start_loc
            binding.distance.text = locallistData.distance.toString()
            binding.heartnum.text = locallistData.heartnum.toString()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LocallistViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userInfo = items[position]
        holder.binding.btnHeart.setOnClickListener() {
        }
        holder.bind(userInfo)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Locallist_data>() {
            override fun areItemsTheSame(
                oldItem: Locallist_data,
                newItem: Locallist_data
            ): Boolean {
                // 현재 노출하고 있는 아이템과 새로운 아이템이 같은지 비교;
                return oldItem.createdAt == newItem.createdAt
            }

            override fun areContentsTheSame(
                oldItem: Locallist_data,
                newItem: Locallist_data
            ): Boolean {
                // equals 비교;
                return oldItem == newItem

            }

        }
    }
}