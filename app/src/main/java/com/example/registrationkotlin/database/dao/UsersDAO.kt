package com.example.registrationkotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.registrationkotlin.database.model.Users

@Dao
interface UsersDAO {
    @Insert
    fun insert(users: Users)

    @Query(value = "Select * from Users")
    fun getAllUsers() : List<Users>
}