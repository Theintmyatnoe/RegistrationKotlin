package com.example.registrationkotlin.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class AssignClass(
    @PrimaryKey
    @NonNull
    var assignClassID:String="",
    var studentID:String="",
    var classID:String="",
    var Active:String="",
    var CreatedBy:String="",
    var CreatedOn:String="",
    var ModifiedBy:String="",
    var ModifiedOn: String=""
) {
}