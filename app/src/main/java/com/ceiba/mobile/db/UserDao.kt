package com.ceiba.mobile.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ceiba.mobile.vo.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(users: MutableList<User>)

    @Query("DELETE FROM `users`")
    fun deleteAll()

    @Transaction
    fun replaceAll(users: MutableList<User>) {
        deleteAll()
        saveAll(users)
    }

    @Transaction
    @Query("SELECT * FROM users WHERE name LIKE :name")
    fun getUsersByUserNameLike(name: String):LiveData<MutableList<User>>


    @Query("SELECT * FROM users")
    fun getAll(): LiveData<MutableList<User>>
}