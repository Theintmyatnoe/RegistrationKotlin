package com.example.registrationkotlin.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Users(
    @PrimaryKey
    @NonNull
    var UserID:String="",
    var UserName:String="",
    var Email:String="",
    var Password:String=""



) {}