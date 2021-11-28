package com.example.withmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.withmeapp.databinding.ActivityLocalBinding
import com.example.withmeapp.databinding.ActivityMainBinding

class LocalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLocalBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}