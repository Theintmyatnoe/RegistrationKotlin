package com.example.registrationkotlin.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrationkotlin.MainActivity
import com.example.registrationkotlin.R
import com.example.registrationkotlin.adapter.AssignClassAdapter
import com.example.registrationkotlin.adapter.AssignCourseAdapter
import com.example.registrationkotlin.database.AppDatabase
import com.example.registrationkotlin.database.model.AssignClass
import com.example.registrationkotlin.database.model.AssignCourse
import com.example.registrationkotlin.delegate.ItemListDelegate
import kotlinx.android.synthetic.main.acitvity_assign_course.*
import kotlinx.android.synthetic.main.activity_assign_class.*

class AssignClassActivity: AppCompatActivity(), View.OnClickListener, ItemListDelegate {
    override fun sendID(classID: String) {
        var intent= Intent(this,AddNewAssignClassActivity::class.java)
        intent.putExtra("ClassID",classID)
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View?) {
        var intent= Intent(this, AddNewAssignClassActivity::class.java)
        startActivity(intent)
        finish()    }

    private var appDatabase: AppDatabase?=null
    private var assingClassList:List<AssignClass> = arrayListOf()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_class)

        supportActionBar?.title = "Assign Class"

        val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        appDatabase= AppDatabase.getDatabase(this)
        assingClassList= appDatabase!!.getAssignClassDAO().getAllAssignClass()

        assign_class_recycler.layoutManager = linearLayoutManager

        val adapter = AssignClassAdapter(this,assingClassList,this)
        assign_class_recycler.adapter=adapter

        fb_add_assign_class.setOnClickListener(this)
    }

    override fun onBackPressed() {
        var intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}