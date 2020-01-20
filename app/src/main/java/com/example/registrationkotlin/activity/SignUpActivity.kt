package com.example.registrationkotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationkotlin.MainActivity
import com.example.registrationkotlin.R
import com.example.registrationkotlin.database.AppDatabase
import com.example.registrationkotlin.database.model.Users
import kotlinx.android.synthetic.main.sign_up_activity.*
import java.util.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.sign_up_activity)

        val database= AppDatabase.getDatabase(this)
        val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("userID")){
            gotoMain()
        }

        btn_register.setOnClickListener {
            var strUserName=edt_userName_in_register.text.toString().trim()
            var strPasword=edt_password_in_register.text.toString().trim()
            var strEmail=edt_email_in_register.text.toString().trim()
            var strConfirmPass=edt_confirm_password_in_register.text.toString().trim()
            if (strUserName == ""){
                edt_userName_in_register.error = "User Name Required"
                edt_userName_in_register.requestFocus()
                return@setOnClickListener
            }
            else if (strEmail == ""){
                edt_email_in_register.error = "Email is required!"
                edt_email_in_register.requestFocus()
                return@setOnClickListener
            }
            else if (strPasword==""){
                edt_password_in_register.error="Password is required!"
                edt_password_in_register.requestFocus()
                return@setOnClickListener
            }
            else if (strConfirmPass==""){
                edt_confirm_password_in_register.error="Those passwords didn't match. Try Again!"
                edt_confirm_password_in_register.requestFocus()
                return@setOnClickListener
            }
            else if (strUserName!="" && strEmail!="" && strEmail!="" && strConfirmPass!=""){
                if (strPasword==strConfirmPass){
                    val users= Users()
                    val userID= UUID.randomUUID().toString()
                    users.UserID=userID
                    users.UserName=strUserName
                    users.Password=strPasword
                    users.Email=strEmail
                    database.getUserDAO().insert(users)

                    Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                    val editor=sharedPreferences.edit()
                    editor.putString("userID",userID)
                    editor.commit()

                    gotoMain()
                }
                else{
                    edt_confirm_password_in_register.error="Those passwords didn't match. Try Again!"
                    edt_confirm_password_in_register.requestFocus()
                    return@setOnClickListener
                }
            }

            else{
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun gotoMain(){
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}