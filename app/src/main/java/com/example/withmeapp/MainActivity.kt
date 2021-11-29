package com.example.withmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.withmeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initNavigation()
    }
    private fun initNavigation() {
        NavigationUI.setupWithNavController(binding.navigationView,findNavController(R.id.navi_host))
    }
    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    var lastTimeBackPressed : Long = 0
    override fun onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed >= 1500){
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this,"'뒤로' 버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_LONG).show() }
        else {
            ActivityCompat.finishAffinity(this)
            System.runFinalization()
            System.exit(0)
        }
    }
}