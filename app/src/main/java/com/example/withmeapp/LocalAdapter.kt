package com.example.withmeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withmeapp.databinding.LocallistViewBinding
import java.nio.file.Files.size

//class LocalAdapter(private val items:MutableList<locallist_data>) :
//class LocalAdapter :
//    RecyclerView.Adapter<LocalAdapter.ViewHolder> () {
//
//    init {  // telephoneBook의 문서를 불러온 뒤 Person으로 변환해 ArrayList에 담음
//        firestore?.collection("user1")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//            // ArrayList 비워줌
//            user1.clear()
//
//            for (snapshot in querySnapshot!!.documents) {
//                var item = snapshot.toObject(Person::class.java)
//                telephoneBook.add(item!!)
//            }
//            notifyDataSetChanged()
//        }
//    }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//            val binding = LocallistViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            return ViewHolder(binding)
//
//        }
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val UserInfo = items[position]
//            holder.bind(UserInfo)
//        }
//        override fun getItemCount(): Int {
//            return items.size
//        }
//
//        class ViewHolder(private val binding: LocallistViewBinding): RecyclerView.ViewHolder(binding.root) {
//            fun bind(users: locallist_data) {
////                binding.userimage =
//                binding.userid.text =
//                binding.away.text =
//                binding.startLoc.text =
//            }
//        }
//    }
//}