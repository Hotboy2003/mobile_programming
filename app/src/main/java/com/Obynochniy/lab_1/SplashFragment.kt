package com.Obynochniy.lab_1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {

        val root = inflater.inflate(R.layout.fragment_splash, container, false)
        val navController = NavHostFragment.findNavController(this)
        val sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)

        val progressBar = root.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = ProgressBar.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            val isRegistered = sharedPreferences.getBoolean("isRegistered", false)
            val isAutoLogin = sharedPreferences.getBoolean("isLoginAutomatic", false)

            when {
                isAutoLogin -> {
                    navController.navigate(R.id.firstFragment)
                }

                isRegistered -> {
                    navController.navigate(R.id.loginFragment)
                }

                else -> {
                    navController.navigate(R.id.registerFragment)
                }
            }
        }, 2000)

        return root
    }
}