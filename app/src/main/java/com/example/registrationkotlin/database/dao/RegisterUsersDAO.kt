package com.example.registrationkotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.registrationkotlin.database.model.RegisterUsers

@Dao
interface RegisterUsersDAO {
    @Insert
    fun insert(users: RegisterUsers)

    @Query(value = "Select * from RegisterUsers")
    fun getAllUsers() : List<RegisterUsers>

    @Query(value = "Select * from RegisterUsers where Type=:type and CreatedBy=:userID")
    fun getAllUsersByType(type: String,userID: String) : List<RegisterUsers>

    @Query(value = "Select * from RegisterUsers where UserID=:userID")
    fun getAllRegisterUserByUserID(userID: String) : List<RegisterUsers>

    @Query(value = "Update RegisterUsers set UserName=:userName,Phone=:phone,Email=:email,Address=:address where UserID=:userID")
    fun updateRegisterUserByUserID(userID: String,userName:String,phone:String,email:String,address: String)

    @Update
    fun update(users:RegisterUsers)
}