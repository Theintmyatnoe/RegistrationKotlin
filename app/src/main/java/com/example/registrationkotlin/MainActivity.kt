package com.example.registrationkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.registrationkotlin.activity.AssignClassActivity
import com.example.registrationkotlin.activity.AssignCourseActivity
import com.example.registrationkotlin.activity.UserRegistrationActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title ="Registration"

        val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("userID")){
            var mUserID=sharedPreferences.getString("userID",null)
        }
        tv_registration.setOnClickListener {
            var intent= Intent(this, UserRegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
        tv_assignClass.setOnClickListener {
            var intent= Intent(this,AssignClassActivity::class.java)
            startActivity(intent)
            finish()
        }
        tv_assignCourse.setOnClickListener {
            var intent= Intent(this, AssignCourseActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}
