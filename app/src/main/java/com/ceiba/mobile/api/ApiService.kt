package com.ceiba.mobile.api

import androidx.lifecycle.LiveData
import com.ceiba.mobile.vo.User
import com.ceiba.mobile.vo.UserPost
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiService {

    @GET
    fun getUsers(@Url url: String): LiveData<ApiResponse<MutableList<User>>>

    @GET
    fun getPost(@Url url: String): Call<MutableList<UserPost>>
}