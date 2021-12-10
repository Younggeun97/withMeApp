package com.example.withmeapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.withmeapp.databinding.FragmentLocalBinding
import com.example.withmeapp.databinding.LocallistViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_local.*
import kotlinx.android.synthetic.main.locallist_view.view.*
import com.google.firebase.firestore.Query


class LocalFragment : Fragment() {
    private var firestore: FirebaseFirestore? = null
    private var uid: String? = null
    private var _binding: FragmentLocalBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    var myRef = FirebaseDatabase.getInstance().getReference("UserList")


    //private val items = mutableListOf<Locallist_data>()
    var items: ArrayList<Locallist_data> = arrayListOf()

    private var localAdapter: LocalAdapter = LocalAdapter(items)

    val listener = object : ChildEventListener {

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val locallistData = snapshot.getValue(Locallist_data::class.java)

            Log.d("test", "test")
            Log.d("test", locallistData.toString())
            locallistData ?: return

            items.add(locallistData) // 리스트에 새로운 항목을 더해서;

            LocalAdapter(items)
        }


        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}

    }


    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    //뷰가 생성되었을 때
    //fragment와 layout을 연결
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()
        database = Firebase.database.reference
        _binding = FragmentLocalBinding.inflate(inflater, container, false)

//        val recyclerView = binding.recyclerview
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = localAdapter
        items.clear() //리스트 초기화;

//        initDB()

//        initLocalRecyclerView()

        // 데이터 가져오기;
//        initListener()


        return binding.root
    }

    private fun initListener() {
        myRef.addChildEventListener(listener)
    }

    private fun initLocalRecyclerView() {
        // activity 일 때는 그냥 this 로 넘겼지만 (그자체가 컨텍스트라서) 그러나
        // 프레그 먼트의 경우에는 아래처럼. context

        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = localAdapter
    }

    private fun initDB() {
        myRef = FirebaseDatabase.getInstance().getReference("UserList")
        //database = Firebase.database.reference.child("UserList") // 디비 가져오기;
    }

    override fun onDestroy() {
        super.onDestroy()

        myRef.removeEventListener(listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        localAdapter.notifyDataSetChanged() // view 를 다시 그림;
    }

    companion object {
        @JvmStatic
        fun newInstance(): LocalFragment {
            return LocalFragment()
        }
    }

}

