package com.example.registrationkotlin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.registrationkotlin.database.model.Course

@Dao
interface CourseDAO {
    @Insert
    fun insert(course: Course)

    @Query(value = "Select * from Course")
    fun getAllCourse() : List<Course>

    @Query(value = "Select * from Course where ClassID=:classID")
    fun getAllCourseByClassID(classID:String) : List<Course>

    @Query(value = "Select * from Course where CourseID=:courseID")
    fun getAllCourseByCourseID(courseID:String) : List<Course>
}