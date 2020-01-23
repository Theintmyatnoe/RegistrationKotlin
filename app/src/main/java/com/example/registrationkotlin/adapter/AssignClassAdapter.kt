package com.example.registrationkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationkotlin.R
import com.example.registrationkotlin.database.AppDatabase
import com.example.registrationkotlin.database.model.AssignClass
import com.example.registrationkotlin.database.model.AssignCourse
import com.example.registrationkotlin.delegate.ItemListDelegate

class AssignClassAdapter (val context: Context, var assignClassList: List<AssignClass>, val delegate: ItemListDelegate): RecyclerView.Adapter<AssignClassAdapter.ViewHolder>() {

    private var contx: Context? =null
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignClassAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.assign_class_list_item, parent, false)
        this.contx=context
        return ViewHolder(v,delegate)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: AssignClassAdapter.ViewHolder, position: Int) {
        holder.bindItems(assignClassList[position],context)

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return assignClassList.size
    }

    //the class is hodling the list view
    class ViewHolder(
        itemView: View,
        delegate: ItemListDelegate
    ) : RecyclerView.ViewHolder(itemView) {
        private val delegate=delegate
        fun bindItems(
            assignClass: AssignClass,
            context: Context
        ) {
            val tvTeacherName = itemView.findViewById(R.id.student_name_info) as TextView
            val tvClassName  = itemView.findViewById(R.id.class_name_info) as TextView
            val appDatabase= AppDatabase.getDatabase(context =context)

            val classID=assignClass.classID
            var classList=appDatabase.getClassesDAO().getAllByClassID(classID)
            for (classes in classList){
                tvClassName.text=classes.ClassName
            }

            val studentID=assignClass.studentID
            var studentList=appDatabase.getRegisterUserDao().getAllRegisterUserByUserID(studentID)
            for (students in studentList){
                tvTeacherName.text=students.UserName
            }

            itemView.setOnClickListener{
                val assignID=assignClass.assignClassID
                delegate.sendID(assignID)
            }


        }
    }
}