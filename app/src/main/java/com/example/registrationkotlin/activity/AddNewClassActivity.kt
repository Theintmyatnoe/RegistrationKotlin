package com.example.registrationkotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationkotlin.R
import com.example.registrationkotlin.database.AppDatabase
import com.example.registrationkotlin.database.model.Classes
import kotlinx.android.synthetic.main.activity_add_new_class.*
import java.util.*


class AddNewClassActivity : AppCompatActivity() {
    private var strClassID:String=""
    private var mUserID:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_class)
        supportActionBar?.title="Add New Class"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("userID")){
            mUserID= sharedPreferences.getString("userID",null).toString()
        }


        val appDatabase=AppDatabase.getDatabase(this)

        btn_save_class.setOnClickListener {
            strClassID= UUID.randomUUID().toString()
            var strClassName=edt_class.text.toString().trim()
            val classes=Classes()
            classes.ClassID=strClassID
            classes.ClassName=strClassName
            classes.Active="1"
            classes.CreatedBy=mUserID
            appDatabase.getClassesDAO().insert(classes)
            Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()
            clearData()

        }


    }
    private fun clearData(){
        edt_class.setText("")
    }

    override fun onSupportNavigateUp(): Boolean {
        goToAssignCourse()
        return true
    }

    override fun onBackPressed() {
        goToAssignCourse()
    }
    private fun goToAssignCourse(){
        val intent=Intent(this,AssignCourseActivity::class.java)
        startActivity(intent)
        finish()
    }
}