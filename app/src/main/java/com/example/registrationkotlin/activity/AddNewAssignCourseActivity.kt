package com.example.registrationkotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationkotlin.MainActivity
import com.example.registrationkotlin.R
import com.example.registrationkotlin.database.AppDatabase
import com.example.registrationkotlin.database.model.AssignCourse
import com.example.registrationkotlin.database.model.Classes
import com.example.registrationkotlin.database.model.Course
import com.example.registrationkotlin.database.model.RegisterUsers
import kotlinx.android.synthetic.main.activity_add_new_assign_course.*
import kotlinx.android.synthetic.main.activity_add_new_course.*
import java.util.*
import kotlin.collections.ArrayList

class AddNewAssignCourseActivity : AppCompatActivity() {

    private var strClassID:String=""
    private var strTeacherID:String=""
    private var strCourseID:String=""
    private var mUserID:String=""

    private var classList:List<Classes>?=null
    private var teacherList:List<RegisterUsers>?=null
    private var courseList:List<Course>?=null

    private var appDatabase: AppDatabase? = null
    private val strClassList = arrayListOf<String>()
    private val strTeacherList = arrayListOf<String>()
    private val strCourseList = arrayListOf<String>()

    private var getAssignIDFromIntent:String=""
    private var assignList:List<AssignCourse> = arrayListOf()

    private var strTeacherName:String?=""
    private var strClassName:String?=""
    private var strCourseName:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_assign_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title ="Add Assign Course"

        val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("userID")){
            mUserID= sharedPreferences.getString("userID",null).toString()
        }

        appDatabase=AppDatabase.getDatabase(this)


        getAssignIDFromIntent= intent?.getStringExtra("AssignID").toString()

        if (getAssignIDFromIntent.isNotEmpty()){
            assignList= appDatabase!!.getAssignCourseDAO().getAllByAssignID(getAssignIDFromIntent)
            if (assignList.isNotEmpty()){
                for (assign in assignList){
                    if (assign.TeacherID.isNotEmpty()){
                        val teacherID=assign.TeacherID
                        val teacherList= appDatabase!!.getRegisterUserDao().getAllRegisterUserByUserID(teacherID)
                        for (teachers in teacherList){
                            strTeacherName=teachers.UserName
                        }
                    }
                    if (assign.ClassID.isNotEmpty()){
                        val classID=assign.ClassID
                        val classList= appDatabase!!.getClassesDAO().getAllByClassID(classID)
                        for (classes in classList){
                            strClassName=classes.ClassName
                        }
                    }
                    if (assign.CourseID.isNotEmpty()){
                        val courseID=assign.CourseID
                        val courseList= appDatabase!!.getCourseDAO().getAllCourseByCourseID(courseID)
                        for (courses in courseList){
                            strCourseName=courses.CourseName
                        }
                    }
                }

            }
        }
        getSpinnerList()
        getCourseListSpinner()
        class_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                strClassID= classList!![position].ClassID
                if (strClassID!=""){
                    getCourseListSpinner()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        teacher_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                strTeacherID= teacherList!![position].UserID
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        course_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                strCourseID= courseList!![position].CourseID
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        btn_save_assign_course.setOnClickListener {
            Log.e("assignID",getAssignIDFromIntent)
            if (getAssignIDFromIntent!="null"){
                val assignCourse=AssignCourse()
                assignCourse.AssignID=getAssignIDFromIntent
                assignCourse.CourseID=strCourseID
                assignCourse.TeacherID=strTeacherID
                assignCourse.ClassID=strClassID
                assignCourse.CreatedBy=mUserID
                assignCourse.Active="1"
                appDatabase!!.getAssignCourseDAO().update(assignCourse)
                Toast.makeText(this,"Successfully updated",Toast.LENGTH_SHORT).show()
                goToAssignCourse()
            }
            if (getAssignIDFromIntent=="null"){
                val assignCourse=AssignCourse()
                val assignID= UUID.randomUUID().toString()
                assignCourse.AssignID=assignID
                assignCourse.CourseID=strCourseID
                assignCourse.TeacherID=strTeacherID
                assignCourse.ClassID=strClassID
                assignCourse.CreatedBy=mUserID
                assignCourse.Active="1"
                appDatabase!!.getAssignCourseDAO().insert(assignCourse)
//            val assignList= appDatabase!!.getAssignCourseDAO().getAllAssignCourse()
                Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                goToAssignCourse()
            }


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
        val intent=Intent(this,AssignCourseActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun getSpinnerList(){
        classList= appDatabase?.getClassesDAO()?.getAllClasses() as ArrayList<Classes>
        teacherList= appDatabase?.getRegisterUserDao()?.getAllUsersByType("Teacher",mUserID) as ArrayList<RegisterUsers>


        if (classList!=null){
            for (classes in classList as ArrayList<Classes>){
                strClassList.add(classes.ClassName)
            }
            val spinnerAdapter=
                ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,strClassList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            class_spinner!!.adapter=spinnerAdapter

            if (strClassName!=""){
                for (i in 1 until  class_spinner.count  ){
                    if (class_spinner.getItemAtPosition(i).toString() == strClassName){
                        class_spinner.setSelection(i)
                    }
                }
            }
        }

        if (teacherList!=null){
            for (teachers in teacherList as ArrayList<RegisterUsers>){
                strTeacherList.add(teachers.UserName)
            }
            val spinnerAdapter=
                ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,strTeacherList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            teacher_spinner!!.adapter=spinnerAdapter

            if (strTeacherName!=""){
                for (i in 1 until  teacher_spinner.count  ){
                    if (teacher_spinner.getItemAtPosition(i).toString() == strTeacherName){
                        teacher_spinner.setSelection(i)
                    }
                }
            }
        }


    }
    private fun getCourseListSpinner(){
        courseList= appDatabase?.getCourseDAO()?.getAllCourseByClassID(strClassID) as ArrayList<Course>
        if (courseList!=null){
            strCourseList.clear()
            for (courses in courseList as ArrayList<Course>){
                strCourseList.add(courses.CourseName)
            }
            val spinnerAdapter=
                ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,strCourseList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            course_spinner!!.adapter=spinnerAdapter

            if (strCourseName!=""){
                for (i in 1 until  course_spinner.count  ){
                    if (course_spinner.getItemAtPosition(i).toString() == strCourseName){
                        course_spinner.setSelection(i)
                    }
                }
            }
        }
    }
}