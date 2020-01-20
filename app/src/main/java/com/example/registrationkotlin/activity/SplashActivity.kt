package com.example.registrationkotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationkotlin.MainActivity
import com.example.registrationkotlin.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        supportActionBar?.hide()
        val sharedPreferences=getSharedPreferences("userID", Context.MODE_PRIVATE)
        val splashImg=findViewById<ImageView>(R.id.img_logo_icon_in_splash)
        Handler().postDelayed({
            if (sharedPreferences.contains("userID") && sharedPreferences.contains("userID")!=null){
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        },3000)
        val animation= AnimationUtils.loadAnimation(this,R.anim.splash_anim)
        splashImg.startAnimation(animation)

    }
}