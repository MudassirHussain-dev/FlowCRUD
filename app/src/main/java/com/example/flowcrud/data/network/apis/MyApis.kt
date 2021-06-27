package com.example.flowcrud.data.network.apis

import com.example.flowcrud.data.model.ResponseGetAllUser
import com.example.flowcrud.data.response.ResponseDelete
import com.example.flowcrud.data.response.ResponseRegister
import com.example.flowcrud.data.response.ResponseUpdate
import retrofit2.Response
import retrofit2.http.*

interface MyApis {

    companion object {
        const val BASE_URL = "http://192.168.2.39/UserApi/"
    }

    @FormUrlEncoded
    @POST("register.php")
    suspend fun registerUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ResponseRegister>

    @GET("fetchusers.php")
    suspend fun getAllUser(): Response<ResponseGetAllUser>

    @FormUrlEncoded
    @POST("deleteaccount.php")
    suspend fun userDelete(
        @Field("id") id: Int
    ): Response<ResponseDelete>

    @FormUrlEncoded
  //  @POST("updateuser.php")
    @POST("updatedata.php")
    suspend fun userUpdate(
        @Field("id") id: Int,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ResponseUpdate>

}