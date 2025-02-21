package com.Obynochniy.lab_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity()
{
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{
            _, dist, _ ->
                when(dist.id){
                    R.id.splashFragment -> bottom_nav.visibility = View.GONE
                    R.id.registerFragment -> bottom_nav.visibility = View.GONE
                    R.id.loginFragment -> bottom_nav.visibility = View.GONE
                    R.id.firstFragment -> bottom_nav.visibility = View.VISIBLE
                    R.id.secondFragment -> bottom_nav.visibility = View.VISIBLE
                }
        }
    }
}