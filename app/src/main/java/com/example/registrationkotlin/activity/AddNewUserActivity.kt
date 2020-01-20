package com.example.registrationkotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationkotlin.R
import com.example.registrationkotlin.database.AppDatabase
import com.example.registrationkotlin.database.model.RegisterUsers
import kotlinx.android.synthetic.main.activity_add_new_user.*
import java.util.*

class AddNewUserActivity : AppCompatActivity() {
    private var mUserID=""
    private var type:String=""
    private var title=""
    private var getUserIDFromIntent:String=""
    private var userID=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_user)

        val database= AppDatabase.getDatabase(this)


        val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("userID")){
            mUserID= sharedPreferences.getString("userID",null).toString()
        }

        type=intent.getStringExtra("type")

        getUserIDFromIntent=intent.getStringExtra("SelectUserID")

//        getUserIDFromIntent=intent.getStringExtra("SelectUserID")


        userID = if (getUserIDFromIntent.isEmpty() && getUserIDFromIntent == ""){
            UUID.randomUUID().toString()

        } else{
            getUserIDFromIntent
        }

        if (getUserIDFromIntent.isNotEmpty() && getUserIDFromIntent != ""){
            val registerUserByID=database.getRegisterUserDao().getAllRegisterUserByUserID(getUserIDFromIntent)
            btn_save_user.text="Update"
            if (registerUserByID.isNotEmpty()){
                for (registerUser in registerUserByID){
                    if (registerUser.UserName!=null) {
                        edt_userName.text= Editable.Factory.getInstance().newEditable(registerUser.UserName)
                    }
                    if (registerUser.Phone!=null) {
                        edt_phone.text= Editable.Factory.getInstance().newEditable(registerUser.Phone)
                    }
                    if (registerUser.Email!=null) {
                        edt_email.text= Editable.Factory.getInstance().newEditable(registerUser.Email)
                    }
                    if (registerUser.Address!=null) {
                        edt_address.text= Editable.Factory.getInstance().newEditable(registerUser.Address)
                    }
                }
            }
        }

        title = if (type=="Student"){
            "Add New Student"
        } else{
            "Add New Teacher"
        }
        supportActionBar?.title = title

        btn_save_user.setOnClickListener {

            var strUserName=edt_userName.text.toString().trim()
            var strEmail=edt_email.text.toString().trim()
            var strPhone=edt_phone.text.toString().trim()
            var strAddress=edt_address.text.toString().trim()

            if (getUserIDFromIntent.isNotEmpty() && getUserIDFromIntent != ""){
                database.getRegisterUserDao().updateRegisterUserByUserID(getUserIDFromIntent,strUserName,strPhone,strEmail,strAddress)
                Toast.makeText(this,"Update", Toast.LENGTH_SHORT).show()
                goRegistration()
            }
            else{
                val registerUsers= RegisterUsers()
                registerUsers.UserID=userID
                registerUsers.UserName=strUserName
                registerUsers.Email=strEmail
                registerUsers.Phone=strPhone
                registerUsers.Address=strAddress
                registerUsers.Active="1"
                registerUsers.CreatedBy=mUserID
                registerUsers.Type=type
                database.getRegisterUserDao().insert(registerUsers)
                Toast.makeText(this,"Save", Toast.LENGTH_SHORT).show()
                goRegistration()
            }
        }

    }

    override fun onBackPressed() {
        goRegistration()
    }
    private fun goRegistration() {
        var intent= Intent(this,UserRegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }
}