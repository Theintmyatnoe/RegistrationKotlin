package com.example.registrationkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.registrationkotlin.activity.AssignClassActivity
import com.example.registrationkotlin.activity.AssignCourseActivity
import com.example.registrationkotlin.activity.LoginActivity
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                var intent= Intent(this, LoginActivity::class.java)
                val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)
                val editor=sharedPreferences.edit()
                editor.clear()
                editor.commit()
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
