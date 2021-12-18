package com.ceiba.mobile.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ceiba.mobile.vo.User

/**
 * Main database description.
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class PruebaDb : RoomDatabase() {
    abstract fun userDao(): UserDao
}