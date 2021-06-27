package com.example.flowcrud.data.model

data class ResponseGetAllUser(
    val error: String,
    val users: List<User>
)