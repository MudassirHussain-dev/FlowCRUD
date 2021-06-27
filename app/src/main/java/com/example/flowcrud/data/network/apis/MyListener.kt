package com.example.flowcrud.data.network.apis

interface MyListener {
    fun onClickData(position: Int, id: Int)
    fun onClickUpdate(position: Int, id: Int, username: String, email: String, password: String)
}