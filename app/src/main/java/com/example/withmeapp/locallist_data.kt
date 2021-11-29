package com.example.withmeapp

import android.media.Image

data class locallist_data(
    val image: Image,
    val userID: String,
    val away: String,
    val start_loc: String,
    val distance: String
)
