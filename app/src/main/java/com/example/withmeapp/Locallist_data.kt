package com.example.withmeapp

data class Locallist_data(
    var userID: String,
    var within: String,
    var start_loc: String,
    var distance: Int,
    var heartnum: Int,
    val createdAt: Long,
){
    // 파이어베이스에 클래스 단위로 올리려면 인자빈생성자 필요;
    constructor() : this("", "", "", 0, 0,0)
}
