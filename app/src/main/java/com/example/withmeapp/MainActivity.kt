package com.example.withmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.withmeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val localFragment = LocalFragment()
    private val joinFragment = JoinFragment()
    private val mypageFragment = MypageFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        private lateinit var binding: ActivityMainBinding
        super.onCreate(savedInstanceState)

        initBinding()
        initNavigation()
    }
    private fun initNavigation() {
        NavigationUI.setupWithNavController(navi, findNavController(R.id.navigationView))
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.act_main)
        binding.lifecycleOwner = this
    }
 }



