package com.example.withmeapp

import android.media.Image
import android.widget.ImageView

data class locallist_data(
    var userimage: ImageView,
    var userID: String,
    var within: Int,
    var start_loc: String,
    var distance: Int,
    var heartnum: Int
)
