package com.example.registrationkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationkotlin.R
import com.example.registrationkotlin.database.AppDatabase
import com.example.registrationkotlin.database.model.AssignCourse
import com.example.registrationkotlin.delegate.ItemListDelegate

class AssignCourseAdapter(val context: Context,var assingCourseList: List<AssignCourse>,val delegate: ItemListDelegate): RecyclerView.Adapter<AssignCourseAdapter.ViewHolder>() {

    private var contx: Context? =null
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignCourseAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.assign_course_list_item, parent, false)
        this.contx=context
        return ViewHolder(v,delegate)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: AssignCourseAdapter.ViewHolder, position: Int) {
        holder.bindItems(assingCourseList[position],context)

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return assingCourseList.size
    }

    //the class is hodling the list view
    class ViewHolder(
        itemView: View,
    delegate: ItemListDelegate) : RecyclerView.ViewHolder(itemView) {
        private val delegate=delegate
        fun bindItems(
            assignCourse: AssignCourse,
            context: Context
        ) {
            val tvTeacherName = itemView.findViewById(R.id.teacher_name_info) as TextView
            val tvCourseName  = itemView.findViewById(R.id.course_name_info) as TextView
            val tvClassName  = itemView.findViewById(R.id.class_name_info) as TextView
            val appDatabase=AppDatabase.getDatabase(context =context)

            val classID=assignCourse.ClassID
            var classList=appDatabase.getClassesDAO().getAllByClassID(classID)
            for (classes in classList){
                tvClassName.text=classes.ClassName
            }

            val teacherID=assignCourse.TeacherID
            var teacherList=appDatabase.getRegisterUserDao().getAllRegisterUserByUserID(teacherID)
            for (teachers in teacherList){
                tvTeacherName.text=teachers.UserName
            }

            val courseID=assignCourse.CourseID
            var courseList=appDatabase.getCourseDAO().getAllCourseByCourseID(courseID)
            for (courses in courseList){
                tvCourseName.text=courses.CourseName
            }
            itemView.setOnClickListener{
                val assignID=assignCourse.AssignID
                delegate.sendID(assignID)
            }


        }
    }
}