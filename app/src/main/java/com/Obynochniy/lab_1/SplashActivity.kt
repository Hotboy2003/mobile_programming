package com.Obynochniy.lab_1

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity()
{

    private lateinit var progressBar: ProgressBar
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progressBar)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        progressBar.visibility = ProgressBar.VISIBLE

        // Задержка для имитации загрузки
        Handler().postDelayed({
            checkUserStatus()
        }, 2000)
    }

    private fun checkUserStatus()
    {
        val email = sharedPreferences.getString("email", null)
        val phone = sharedPreferences.getString("phone", null)
        val password = sharedPreferences.getString("password", null)
        val isAutoLogin = sharedPreferences.getBoolean("isAutoLogin", false)

        when
        {
            (email != null || phone != null) && password != null && isAutoLogin ->
            {
                startActivity(Intent(this, ContentActivity::class.java))
            }
            (email != null || phone != null) && password != null ->
            {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            else ->
            {
                startActivity(Intent(this, RegistrationActivity::class.java))
            }
        }
        finish()
    }
}