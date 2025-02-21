package com.Obynochniy.lab_1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment()
{
    private var isPhoneRegistration = 1
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val root = inflater.inflate(R.layout.fragment_register, container, false)
        val navController = NavHostFragment.findNavController(this)
        sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)

        val phoneRegistration = root.findViewById<Button>(R.id.phoneRegistrationButton)
        val emailRegistration = root.findViewById<Button>(R.id.emailRegistrationButton)
        val emailEditText = root.findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = root.findViewById<EditText>(R.id.passwordEditText)
        val confirmPasswordEditText = root.findViewById<EditText>(R.id.confirmPasswordEditText)
        val registerButton = root.findViewById<Button>(R.id.registerButton)

        phoneRegistration.setOnClickListener {
            val color = ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark)
            phoneRegistration.setTextColor(color)

            val grayColor = Color.GRAY
            emailRegistration.setTextColor(grayColor)

            emailEditText.hint = "Введите номер телефона"
            emailEditText.inputType = InputType.TYPE_CLASS_PHONE

            isPhoneRegistration = 1
        }

        emailRegistration.setOnClickListener {
            val color = ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark)
            emailRegistration.setTextColor(color)

            val grayColor = Color.GRAY
            phoneRegistration.setTextColor(grayColor)

            emailEditText.hint = "Введите email"
            emailEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

            isPhoneRegistration = 0
        }

        registerButton.setOnClickListener {
            if (validateInputs(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    confirmPasswordEditText.text.toString(),
                    isPhoneRegistration
                ))
            {
                saveUserData(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    navController
                )
//                navController.navigate(R.id.firstFragment)
            }
        }
        return root
    }

    private fun validateInputs(
        emailOrPhone: String,
        password: String,
        confirmPassword: String,
        isPhoneRegistration: Int
    ): Boolean
    {
        if (isPhoneRegistration == 0)
        {
            if (emailOrPhone.isEmpty())
            {
                showToast("Введите email")
                return false
            }
            if (!emailOrPhone.contains("@"))
            {
                showToast("Email должен содержать @")
                return false
            }
            if (!validatePassword(password, confirmPassword))
            {
                return false
            }
            return true
        }
        else
        {
            if (emailOrPhone.isEmpty())
            {
                showToast("Введите номер телефона")
                return false
            }
            if (!emailOrPhone.contains("+"))
            {
                showToast("Номер телефона должен содержать +")
                return false
            }
            if (!validatePassword(password, confirmPassword))
            {
                return false
            }
            return true
        }
    }

    private fun validatePassword(password: String, confirmPassword: String): Boolean
    {
        if (password.length < 8)
        {
            showToast("Пароль должен содержать минимум 8 символов")
            return false
        }
        else if (password != confirmPassword)
        {
            showToast("Пароли не совпадают")
            return false
        }
        return true
    }

    private fun saveUserData(emailOrPhone: String, password: String, navController: NavController)
    {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(emailOrPhone, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val editor = sharedPreferences.edit()
                editor.putBoolean("isRegistered", true).apply()
                navController.navigate(R.id.firstFragment)
            }
        }.addOnFailureListener {
            exception -> Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
        }
        //showToast("Регистрация прошла успешно")
//        val editor = sharedPreferences.edit()
//        if (isPhoneRegistration == 0)
//        {
//            editor.putString("email", emailOrPhone)
//        }
//        else
//        {
//            editor.putString("phone", emailOrPhone)
//        }
//        editor.putString("password", password)
//        editor.apply()
    }

    private fun showToast(message: String)
    {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
