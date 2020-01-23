package com.example.registrationkotlin.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationkotlin.MainActivity
import com.example.registrationkotlin.R
import com.example.registrationkotlin.database.AppDatabase
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {
    private var strUserName = ""
    private var strPassword=""
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        supportActionBar?.hide()
        val animation= AnimationUtils.loadAnimation(this,R.anim.bounce_anim)
        animation.repeatMode = Animation.REVERSE
        animation.duration = (1000..2000).random().toLong()
        layout_login.startAnimation(animation)

        val appDatabase=AppDatabase.getDatabase(this)


        val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)


        btn_login.setOnClickListener {
            strUserName=edt_userName.text.toString().trim()
            strPassword=edt_password.text.toString().trim()

            if (strUserName == ""){
                edt_userName.error = "Email required!"
                edt_userName.requestFocus()
                return@setOnClickListener
            }
            else if (strPassword == ""){
                edt_password.error = "Password required!"
                edt_password.requestFocus()
                return@setOnClickListener
            }

            if (strUserName!="" && strPassword!=""){
                val loginUser=appDatabase.getUserDAO().getUserByEmailAndPass(strUserName,strPassword)
                if (loginUser.isNotEmpty()){
                    val editor=sharedPreferences.edit()
                    editor.putString("userID", loginUser[0].UserID)
                    editor.commit()
                    gotoMain()
                }
                else{
                    Toast.makeText(this,"Username/Password error", Toast.LENGTH_LONG).show()
                }
            }


        }

        tv_sign_up.setOnClickListener{
            tv_sign_up.setTextColor(Color.parseColor("#56a6e7"))
            val intent= Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun gotoMain(){
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}