package com.example.registrationkotlin.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrationkotlin.MainActivity
import com.example.registrationkotlin.R
import com.example.registrationkotlin.adapter.AssignCourseAdapter
import com.example.registrationkotlin.database.AppDatabase
import com.example.registrationkotlin.database.model.AssignCourse
import com.example.registrationkotlin.delegate.ItemListDelegate
import kotlinx.android.synthetic.main.acitvity_assign_course.*
import kotlinx.android.synthetic.main.content_floating_btn.*

class AssignCourseActivity : AppCompatActivity(), ItemListDelegate {
    override fun sendID(userID: String) {
        var intent= Intent(this,AddNewAssignCourseActivity::class.java)
        intent.putExtra("AssignID",userID)
        startActivity(intent)
        finish()
    }

    private var isOpen:Boolean=false

    private var appDatabase:AppDatabase?=null
    private var assingCourseList:List<AssignCourse> = arrayListOf()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_assign_course)

        supportActionBar?.title = "Assign Course"

        val fabClose=AnimationUtils.loadAnimation(this,R.anim.fab_close)
        val fabOpen=AnimationUtils.loadAnimation(this,R.anim.fab_open)
        val fabAntiClock=AnimationUtils.loadAnimation(this,R.anim.fab_rotate_anticlock)
        val fabClock=AnimationUtils.loadAnimation(this,R.anim.fab_rotate_clock)

        appDatabase= AppDatabase.getDatabase(this)
        assingCourseList= appDatabase!!.getAssignCourseDAO().getAllAssignCourse()

        val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        assign_course_recycler.layoutManager = linearLayoutManager

        val adapter = AssignCourseAdapter(this,assingCourseList,this)
        assign_course_recycler.adapter=adapter

        fab_add_assign.setOnClickListener {
            if (isOpen){
                tv_class.visibility=View.INVISIBLE
                tv_assign.visibility=View.INVISIBLE
                tv_course.visibility=View.INVISIBLE
                fb_assign.startAnimation(fabClose)
                fb_class.startAnimation(fabClose)
                fb_course.startAnimation(fabClose)
                fab_add_assign.startAnimation(fabAntiClock)
                fb_assign.isClickable=false
                fb_course.isClickable=false
                fb_class.isClickable=false
                isOpen=false
            }
            else{
                tv_class.visibility=View.VISIBLE
                tv_assign.visibility=View.VISIBLE
                tv_course.visibility=View.VISIBLE
                fb_assign.startAnimation(fabOpen)
                fb_class.startAnimation(fabOpen)
                fb_course.startAnimation(fabOpen)
                fab_add_assign.startAnimation(fabClock)
                fb_assign.isClickable=true
                fb_course.isClickable=true
                fb_class.isClickable=true
                isOpen=true
            }
        }
        fb_class.setOnClickListener {
            var intent= Intent(this, AddNewClassActivity::class.java)
            startActivity(intent)
            finish()
        }
        fb_course.setOnClickListener {
            var intent= Intent(this, AddNewCourseActivity::class.java)
            startActivity(intent)
            finish()
        }
        fb_assign.setOnClickListener {
            var intent= Intent(this, AddNewAssignCourseActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        var intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}