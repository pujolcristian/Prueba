package com.ceiba.mobile.view.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.ceiba.mobile.api.ApiCallback
import com.ceiba.mobile.repository.UserRepository
import com.ceiba.mobile.vo.User
import com.ceiba.mobile.vo.UserPost
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    val userData = MediatorLiveData<User>()

    private val _isLoading = MediatorLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUserPosts = MediatorLiveData<MutableList<UserPost>>()
    val listUserPosts: LiveData<MutableList<UserPost>> = _listUserPosts

    fun getUserPost(userId: Int) {
        _isLoading.value = true

        userRepository.getPost(userId, object : ApiCallback<MutableList<UserPost>> {
            override fun onSuccess(data: MutableList<UserPost>?) {
                _isLoading.postValue(false)
                if (data != null) {
                    _listUserPosts.postValue(data!!)
                }
            }

            override fun onError(error: String) {
                _isLoading.postValue(false)
            }

        })
    }

    fun setUserData(user: User) {
        userData.value = user
    }
}