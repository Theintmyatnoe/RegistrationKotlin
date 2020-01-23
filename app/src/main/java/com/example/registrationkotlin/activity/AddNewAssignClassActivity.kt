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
import com.example.registrationkotlin.database.model.AssignClass
import com.example.registrationkotlin.database.model.AssignCourse
import com.example.registrationkotlin.database.model.Classes
import com.example.registrationkotlin.database.model.RegisterUsers
import kotlinx.android.synthetic.main.activity_add_new_assign_class.*
import kotlinx.android.synthetic.main.activity_add_new_assign_course.*
import java.util.*
import kotlin.collections.ArrayList

class AddNewAssignClassActivity: AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
       if (getAssignIDFromIntent!="null"){
           val assignClass=AssignClass()
           assignClass.assignClassID=getAssignIDFromIntent
           assignClass.classID=strClassID
           assignClass.studentID=strStudentID
           assignClass.CreatedBy= mUserID.toString()
           assignClass.Active="1"
           appDatabase?.getAssignClassDAO()?.update(assignClass)
           Toast.makeText(this,"save success",Toast.LENGTH_SHORT).show()
           goToAssignClass()
       }
        else{
           val strClassAssignID=UUID.randomUUID().toString()
           val assignClass=AssignClass()
           assignClass.assignClassID=strClassAssignID
           assignClass.classID=strClassID
           assignClass.studentID=strStudentID
           assignClass.CreatedBy= mUserID.toString()
           assignClass.Active="1"
           appDatabase?.getAssignClassDAO()?.insert(assignClass)
           Toast.makeText(this,"save success",Toast.LENGTH_SHORT).show()
           goToAssignClass()
       }

    }

    private var mUserID: String? =null

    private var strClassID:String=""
    private var strStudentID:String=""

    private var strStudentName:String?=""
    private var strClassName:String?=""

    private var classList:List<Classes>?=null
    private var studentList:List<RegisterUsers>?=null

    private var appDatabase:AppDatabase?=null

    private val strClassList = arrayListOf<String>()
    private val strStudentList = arrayListOf<String>()

    private var getAssignIDFromIntent:String=""
    private var assignClassList:List<AssignClass> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_assign_class)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title ="Add Assign Class"

        val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("userID")){
            mUserID= sharedPreferences.getString("userID",null)
        }

        appDatabase= AppDatabase.getDatabase(this)

        getAssignIDFromIntent= intent?.getStringExtra("ClassID").toString()

        if (getAssignIDFromIntent.isNotEmpty()){
            assignClassList= appDatabase!!.getAssignClassDAO().getAllByAssignID(getAssignIDFromIntent)
            if (assignClassList.isNotEmpty()){
                for (assign in assignClassList){
                    if (assign.studentID.isNotEmpty()){
                        val studentID=assign.studentID
                        val studentList= appDatabase!!.getRegisterUserDao().getAllRegisterUserByUserID(studentID)
                        for (students in studentList){
                            strStudentName=students.UserName
                        }
                    }
                    if (assign.classID.isNotEmpty()){
                        val classID=assign.classID
                        val classList= appDatabase!!.getClassesDAO().getAllByClassID(classID)
                        for (classes in classList){
                            strClassName=classes.ClassName
                        }
                    }
                }

            }
        }

        getSpinnerList()
        class_spinner_in_assign_class.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                strClassID= classList!![position].ClassID

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        student_spinner_in_assign_class.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                strStudentID= studentList!![position].UserID
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        btn_save_assign_class.setOnClickListener(this)

    }

    private fun getSpinnerList(){
        classList= appDatabase?.getClassesDAO()?.getAllClasses() as ArrayList<Classes>
        studentList= mUserID?.let {appDatabase?.getRegisterUserDao()?.getAllUsersByType("Student",it)} as ArrayList<RegisterUsers>


        if (classList!=null){
            for (classes in classList as ArrayList<Classes>){
                strClassList.add(classes.ClassName)
            }
            val spinnerAdapter=
                ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,strClassList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            class_spinner_in_assign_class!!.adapter=spinnerAdapter

            if (strClassName!=""){
                for (i in 1 until  class_spinner_in_assign_class.count  ){
                    if (class_spinner_in_assign_class.getItemAtPosition(i).toString() == strClassName){
                        class_spinner_in_assign_class.setSelection(i)
                    }
                }
            }
        }

        if (studentList!=null){
            for (teachers in studentList as ArrayList<RegisterUsers>){
                strStudentList.add(teachers.UserName)
            }
            val spinnerAdapter=
                ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,strStudentList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            student_spinner_in_assign_class!!.adapter=spinnerAdapter

            if (strStudentName!=""){
                for (i in 1 until  student_spinner_in_assign_class.count  ){
                    if (student_spinner_in_assign_class.getItemAtPosition(i).toString() == strStudentName){
                        student_spinner_in_assign_class.setSelection(i)
                    }
                }
            }
        }


    }
    override fun onSupportNavigateUp(): Boolean {
        goToAssignClass()
        return true
    }

    override fun onBackPressed() {
        goToAssignClass()
    }
    private fun goToAssignClass(){
        val intent= Intent(this,AssignClassActivity::class.java)
        startActivity(intent)
        finish()
    }
}