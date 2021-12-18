package com.ceiba.mobile.repository

import android.os.AsyncTask.execute
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ceiba.mobile.api.ApiCallback
import com.ceiba.mobile.api.ApiResponse
import com.ceiba.mobile.api.ApiService
import com.ceiba.mobile.db.UserDao
import com.ceiba.mobile.utils.AppExecutors
import com.ceiba.mobile.vo.Resource
import com.ceiba.mobile.vo.User
import com.ceiba.mobile.vo.UserPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
) {

    fun getUsers(name: String?): LiveData<Resource<MutableList<User>>> {
        return object : NetworkBoundResource<MutableList<User>, MutableList<User>>(appExecutors) {
            override fun saveCallResult(item: MutableList<User>) {
                appExecutors.diskIO().execute {
                    userDao.replaceAll(item)
                }
            }

            override fun shouldFetch(data: MutableList<User>?): Boolean {
                return data?.size?: 0 == 0
            }

            override fun loadFromDb(): LiveData<MutableList<User>> {
                return if (name.isNullOrEmpty()) {
                    Transformations.map(userDao.getAll()) {
                        return@map it
                    }
                } else {
                    Transformations.map(userDao.getUsersByUserNameLike("%$name%")) {
                        return@map it
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<MutableList<User>>> {
                val url = "https://jsonplaceholder.typicode.com/users"
                return apiService.getUsers(url)

            }
        }.asLiveData()
    }

    fun getPost(userId: Int, callback: ApiCallback<MutableList<UserPost>>) {
        val url = "https://jsonplaceholder.typicode.com/posts?userId=$userId"

        val call = apiService.getPost(url)

        call.enqueue(object : Callback<MutableList<UserPost>> {
            override fun onResponse(
                call: Call<MutableList<UserPost>>,
                response: Response<MutableList<UserPost>>
            ) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body())
                } else {
                    callback.onError(response.errorBody()?.string() ?: " ")
                }
            }

            override fun onFailure(call: Call<MutableList<UserPost>>, t: Throwable) {
                callback.onError(t.message ?: " ")
            }

        })
    }

    fun getUsersByUserNameLike(name: String?): LiveData<MutableList<User>> {
        return userDao.getUsersByUserNameLike("%$name%")
    }
}