package com.example.registrationkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.registrationkotlin.database.dao.*
import com.example.registrationkotlin.database.model.*

@Database(entities = [Users::class, RegisterUsers::class,Classes::class,Course::class,AssignCourse::class,AssignClass::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDAO(): UsersDAO
    abstract fun getRegisterUserDao(): RegisterUsersDAO
    abstract fun getClassesDAO():ClassesDAO
    abstract fun getCourseDAO():CourseDAO
    abstract fun getAssignCourseDAO():AssignCourseDAO
    abstract fun getAssignClassDAO():AssignClassDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo_database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}