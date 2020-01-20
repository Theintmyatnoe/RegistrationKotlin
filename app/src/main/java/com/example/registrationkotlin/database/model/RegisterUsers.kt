package com.example.registrationkotlin.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RegisterUsers(
    @PrimaryKey
    @NonNull
    var UserID:String="",
    var UserName:String="",
    var Email:String="",
    var Phone:String="",
    var Address:String="",
    var Type:String="",
    var Active:String="",
    var CreatedBy:String="",
    var CreatedOn:String="",
    var ModifiedBy:String="",
    var ModifiedOn: String=""
) {

}