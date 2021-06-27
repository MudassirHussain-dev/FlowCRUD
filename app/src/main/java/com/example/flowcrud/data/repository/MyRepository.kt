package com.example.flowcrud.data.repository

import com.example.flowcrud.data.model.ResponseGetAllUser
import com.example.flowcrud.data.network.apis.MyApis
import com.example.flowcrud.data.response.ResponseDelete
import com.example.flowcrud.data.response.ResponseRegister
import com.example.flowcrud.data.response.ResponseUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MyRepository @Inject constructor(private val myApis: MyApis) {

    fun registerUser(
        username: String,
        email: String,
        password: String
    ): Flow<Response<ResponseRegister>> = flow {
        emit(myApis.registerUser(username, email, password))
    }.flowOn(Dispatchers.IO)

    fun getAllUser(): Flow<Response<ResponseGetAllUser>> = flow {
        emit(myApis.getAllUser())
    }.flowOn(Dispatchers.IO)

    fun userDelete(id: Int): Flow<Response<ResponseDelete>> = flow {
        emit(myApis.userDelete(id))
    }.flowOn(Dispatchers.IO)

    fun userUpdate(
        id: Int,
        username: String,
        email: String,
        password: String
    ): Flow<Response<ResponseUpdate>> = flow {
        emit(myApis.userUpdate(id, username, email,password))
    }.flowOn(Dispatchers.IO)

}