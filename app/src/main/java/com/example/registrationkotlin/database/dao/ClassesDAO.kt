package com.example.registrationkotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.registrationkotlin.database.model.Classes

@Dao
interface ClassesDAO {
    @Insert
    fun insert(classes: Classes)

    @Query(value = "Select * from Classes")
    fun getAllClasses() : List<Classes>

    @Query(value = "Select * from Classes where ClassID=:classID")
    fun getAllByClassID(classID:String) : List<Classes>
}