package com.example.withmeapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.telephony.CarrierConfigManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.withmeapp.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.gms.maps.GoogleMap as GoogleMap

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class HomeFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap


    val handler = Handler()
    var timeValue = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment

        mapFragment.getMapAsync(this)




//        binding.maincompass.setOnClickListener {
//            val intent = Intent(getActivity(), MapsActivity::class.java)
//            startActivity(intent)
//        }


        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        val runnable = object : Runnable {
            override fun run() {
                timeValue++
                //TextView 업데이트 하기
                timeToText(timeValue)?.let {
                    timeView.text = it
                }
                handler.postDelayed(this, 1000) //1초 간격

            }
        }





        var isReset = false
        var isstartbtnClick = false
        var isstopbtnClick = false

        //버튼 이벤트 처리
        binding.startbtn.setOnClickListener {

            if(isReset == false) { // false  초기화 유도, true  진행
                Toast.makeText(activity, "Reset으로 초기화 해주세요.", Toast.LENGTH_SHORT).show()
            }
            if (isstartbtnClick == true) { // 시작 버튼이 눌렸는데 유저가 다시 한번 누른 경우
                Toast.makeText(activity, "이미 시작되었습니다.", Toast.LENGTH_SHORT).show()
            }

            // Flag 설정
            isReset = false
            isstartbtnClick = true

            handler.post(runnable)

            // 버튼 활성화 상태 변경
            binding.startbtn.isEnabled = false
            binding.stopbtn.isEnabled = true

            }

        binding.stopbtn.setOnClickListener {
            handler.removeCallbacks(runnable)

            binding.stopbtn.isEnabled = false
            binding.resetbtn.isEnabled = true
        }

        binding.resetbtn.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = -1
            timeToText()?.let {
                timeView.text = it
            }

            binding.startbtn.isEnabled = true
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

    companion object {

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val marker = LatLng(35.241615, 128.695587)
        mMap.addMarker(MarkerOptions().position(marker).title("Marker LAB"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f))

    }




}
