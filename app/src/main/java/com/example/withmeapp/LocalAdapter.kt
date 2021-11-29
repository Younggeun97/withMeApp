package com.example.withmeapp
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.withmeapp.databinding.LocallistViewBinding
//import java.nio.file.Files.size
//
//@SuppressLint("NotifyDataSetChanged")
//class LocalAdapter :
//    RecyclerView.Adapter<LocalAdapter.ViewHolder> () {
//
//    var items : ArrayList<locallist_data> = arrayListOf()
//
//    init {  // items의 문서를 불러온 뒤 locallist_data으로 변환해 ArrayList에 담음
//        firestore?.collection("UserList")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//            // ArrayList 비워줌
//            items.clear()
//
//            for (snapshot in querySnapshot!!.documents) {
//                val item = snapshot.toObject(locallist_data::class.java)
//                items.add(item!!)
//            }
//            notifyDataSetChanged()
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        val binding = LocallistViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//
//    }
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val UserInfo = items[position]
//        holder.bind(UserInfo)
//    }
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    inner class ViewHolder(private val binding: LocallistViewBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(users: locallist_data) {
////                binding.userimage = users.userimage
//            binding.userid.text = users.userID
//            binding.within.text = users.within.toString()
//            binding.startLoc.text = users.start_loc
//            binding.distance.text = users.distance.toString()
//            binding.heartnum.text = users.heartnum.toString()
//        }
//    }
//}
//}
