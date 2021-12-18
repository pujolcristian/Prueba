package com.ceiba.mobile.api

interface ApiCallback<T> {
    fun onSuccess(data: T?)
    fun onError(error: String)
}