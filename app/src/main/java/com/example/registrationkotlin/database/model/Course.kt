package com.example.registrationkotlin.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Course(
    @PrimaryKey
    @NonNull
    var CourseID:String="",
    var CourseName:String="",
    var ClassID:String="",
    var Active:String="",
    var CreatedBy:String="",
    var CreatedOn:String="",
    var ModifiedBy:String="",
    var ModifiedOn: String=""
) {
}