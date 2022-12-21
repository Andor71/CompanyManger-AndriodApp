package com.zoltanlorinczi.project_retrofit


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.ActivityMainBinding
import com.zoltanlorinczi.project_retrofit.fragment.LoginFragment
import com.zoltanlorinczi.project_retrofit.fragment.SplashScreen


class MainActivity : AppCompatActivity() {

    val TAG: String = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.tasks -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.listFragment)
                }
                R.id.groups -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.groupsListFragment)
                }
                R.id.activites -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.activitesFragment)
                }
                R.id.settings ->{
                    findNavController(R.id.nav_host_fragment).navigate(R.id.settings);
                }
                else ->{
                    false
                }
            }
            true
        }

    }
}