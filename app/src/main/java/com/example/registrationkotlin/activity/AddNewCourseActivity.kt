package com.example.registrationkotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationkotlin.R
import com.example.registrationkotlin.database.AppDatabase
import com.example.registrationkotlin.database.model.Classes
import com.example.registrationkotlin.database.model.Course
import com.example.registrationkotlin.database.model.RegisterUsers
import kotlinx.android.synthetic.main.activity_add_new_course.*
import java.util.*
import kotlin.collections.ArrayList

class AddNewCourseActivity : AppCompatActivity() {
    private var mUserID:String=""
    private var classID:String?=""

    private var classList:List<Classes>?=null
    private var appDatabase: AppDatabase? = null
    private val strClassList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_course)
        supportActionBar?.title="Add New Course"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("userID")){
            mUserID= sharedPreferences.getString("userID",null).toString()
        }


        appDatabase= AppDatabase.getDatabase(this)
        getClassSpinner()

        class_spinner_in_course.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected( parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                classID= classList!![position].ClassID
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        btn_save_course.setOnClickListener {
            val courseID= UUID.randomUUID().toString()
            val course=Course()
            course.CourseID=courseID
            course.ClassID= this!!.classID!!
            course.CourseName=edt_course.text.toString().trim()
            course.CreatedBy=mUserID
            appDatabase!!.getCourseDAO().insert(course)
            Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()
            edt_course.setText("")
        }

    }

    private fun getClassSpinner(){
        classList= appDatabase?.getClassesDAO()?.getAllClasses() as ArrayList<Classes>
        if (classList!=null){
            for (classes in classList as ArrayList<Classes>){
                strClassList.add(classes.ClassName)
            }
            val spinnerAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,strClassList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            class_spinner_in_course!!.adapter=spinnerAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        goToAssignCourse()
        return true
    }

    override fun onBackPressed() {
        goToAssignCourse()
    }
    private fun goToAssignCourse(){
        val intent= Intent(this,AssignCourseActivity::class.java)
        startActivity(intent)
        finish()
    }
}