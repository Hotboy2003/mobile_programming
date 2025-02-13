package com.Obynochniy.lab_1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity()
{
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val emailOrPhone = findViewById<EditText>(R.id.emailOrPhone)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val isLoginAutomatic = findViewById<CheckBox>(R.id.isLoginAutomatic)

        loginButton.setOnClickListener {
            val emailOrPhone = emailOrPhone.text.toString()
            val password = password.text.toString()

            if (validateLogin(emailOrPhone, password))
            {
                showToast("Вход выполнен успешно")
                saveLoginPreference(isLoginAutomatic.isChecked)
                startActivity(Intent(this, ContentActivity::class.java))
                finish()
            }
            else
            {
                showToast("Неправильный email/телефон или пароль")
            }
        }
    }

    private fun validateLogin(emailOrPhone: String, password: String): Boolean
    {
        val savedEmail = sharedPreferences.getString("email", null)
        val savedPhone = sharedPreferences.getString("phone", null)
        val savedPassword = sharedPreferences.getString("password", null)

        return (emailOrPhone == savedEmail || emailOrPhone == savedPhone) && password == savedPassword
    }

    private fun saveLoginPreference(isChecked: Boolean)
    {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoginAutomatic", isChecked)
        editor.apply()
    }

    private fun showToast(message: String)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
