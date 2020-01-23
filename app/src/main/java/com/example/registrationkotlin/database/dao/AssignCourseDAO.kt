package com.example.registrationkotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.registrationkotlin.database.model.AssignCourse
@Dao
interface AssignCourseDAO {
    @Insert
    fun insert(assignCourse: AssignCourse)

    @Update
    fun update(assignCourse: AssignCourse)

    @Query(value = "Select * from AssignCourse")
    fun getAllAssignCourse() : List<AssignCourse>

    @Query(value = "Select * from AssignCourse where AssignID=:assignID")
    fun getAllByAssignID(assignID:String) : List<AssignCourse>
}