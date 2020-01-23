package com.example.registrationkotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.registrationkotlin.database.model.AssignClass

@Dao
interface AssignClassDAO {
    @Insert
    fun insert(assignClass: AssignClass)

    @Update
    fun update(assignClass: AssignClass)

    @Query(value = "Select * from AssignClass")
    fun getAllAssignClass() : List<AssignClass>

    @Query(value = "Select * from AssignClass where assignClassID=:assignID")
    fun getAllByAssignID(assignID:String) : List<AssignClass>
}