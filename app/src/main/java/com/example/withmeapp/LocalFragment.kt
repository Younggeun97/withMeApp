package com.example.withmeapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.withmeapp.LocalActivity.LocalAdapter
import com.example.withmeapp.databinding.FragmentLocalBinding
import com.example.withmeapp.databinding.LocallistViewBinding
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.locallist_view.view.*


class LocalFragment : Fragment() {

    private var _binding: FragmentLocalBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    var items : ArrayList<Locallist_data> = arrayListOf()

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // 파이어스토어 인스턴스 초기화
//        firestore = FirebaseFirestore.getInstance()
//
//        binding.recyclerview.adapter = LocalAdapter()
//        binding.recyclerview.layoutManager = LinearLayoutManager(this)

    }

    //뷰가 생성되었을 때
    //fragment와 layout을 연결
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        database = Firebase.database.reference
        _binding = FragmentLocalBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = LocalAdapter()

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(): LocalFragment {
            return LocalFragment()
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    inner class LocalAdapter :
        RecyclerView.Adapter<LocalAdapter.ViewHolder> () {


        init {  // items의 문서를 불러온 뒤 locallist_data으로 변환해 ArrayList에 담음
            FirebaseDatabase.getInstance().reference.child("UserList").addValueEventListener(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    // ArrayList 비워줌
                    items.clear()

                    for(data in snapshot.children){
                        val item = data.getValue<Locallist_data>()
                        items.add(item!!)
                    }

                    notifyDataSetChanged()
                }
            })
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val binding = LocallistViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)

        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val userInfo = items[position]


            holder.binding.btnHeart.setOnClickListener(){
                binding.recyclerview.heartnum

            }
            holder.bind(userInfo)
        }
        override fun getItemCount(): Int {
            return items.size
        }

        inner class ViewHolder(val binding: LocallistViewBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(users: Locallist_data) {
                binding.userid.text = users.userID
                binding.within.text = users.within.toString()
                binding.startLoc.text = users.start_loc
                binding.distance.text = users.distance.toString()
                binding.heartnum.text = users.heartnum.toString()
            }
        }
    }
}


