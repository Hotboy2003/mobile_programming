package com.Obynochniy.lab_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment()
{
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        root = inflater.inflate(R.layout.fragment_login, container, false)
        val navController = NavHostFragment.findNavController(this)
        sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)

        root.loginButton.setOnClickListener{
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(root.emailOrPhone.text.toString(), root.password.text.toString()).addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    saveLoginPreference(isLoginAutomatic.isChecked)
                    navController.navigate(R.id.firstFragment)
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

//
//        val emailOrPhone = root.findViewById<EditText>(R.id.emailOrPhone)
//        val password = root.findViewById<EditText>(R.id.password)
//        val loginButton = root.findViewById<Button>(R.id.loginButton)
//        val isLoginAutomatic = root.findViewById<CheckBox>(R.id.isLoginAutomatic)
//
//        loginButton.setOnClickListener {
//            val emailOrPhone = emailOrPhone.text.toString()
//            val password = password.text.toString()
//
//            if (validateLogin(emailOrPhone, password))
//            {
//                showToast("Вход выполнен успешно")
//                saveLoginPreference(isLoginAutomatic.isChecked)
//                navController.navigate(R.id.firstFragment)
//            }
//            else
//            {
//                showToast("Неправильный email/телефон или пароль")
//            }
//        }
        return root
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
