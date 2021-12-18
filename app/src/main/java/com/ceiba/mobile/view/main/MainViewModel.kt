package com.ceiba.mobile.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ceiba.mobile.api.ApiCallback
import com.ceiba.mobile.repository.UserRepository
import com.ceiba.mobile.vo.Resource
import com.ceiba.mobile.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.logging.Filter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _name = MediatorLiveData<String>()
    private val _initUserData = MediatorLiveData<Boolean>()

    init {
        _initUserData.value = true
    }

    var users: LiveData<Resource<MutableList<User>>> = Transformations.switchMap(_initUserData){
        val users = userRepository.getUsers(_name.value)
        users
    }


    fun setSearch(name: String) {
        _name.value = name
    }

    fun setInitUserData(boolean: Boolean){
        _initUserData.value = boolean
    }
}