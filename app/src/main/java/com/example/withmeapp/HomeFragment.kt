package com.example.withmeapp

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.withmeapp.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.locallist_view.*
import android.R
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val handler = Handler()
    var timeValue = -1

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        binding.maincompass.setOnClickListener {
            val intent = Intent(getActivity(), MapsActivity::class.java)
            startActivity(intent)
        }


        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        val runnable = object : Runnable {
            override fun run() {
                timeValue++
                //TextView 업데이트 하기
                timeToText(timeValue)?.let {
                    timeView.text = it
                }
                handler.postDelayed(this, 1000)
            }
        }

        //버튼 이벤트 처리

        binding.startbtn.setOnClickListener {
            handler.post(runnable)
        }

        binding.stopbtn.setOnClickListener {

            handler.removeCallbacks(runnable)
            val datas = mapOf<String, Any>(
                "userID" to userID,
                "within" to within,
                "start_loc" to start_loc,
                "distance" to distance,
                "heartnum" to heartnum
            )

            db.collection("UserList")
                .add(datas)

            (activity as MainActivity).change()


        }

        binding.resetbtn.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = -1
            timeToText()?.let {
                timeView.text = it
            }
        }



        return binding.root
    }

    //변환작업
    private fun timeToText(time: Int = 0): String? {
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }


}
