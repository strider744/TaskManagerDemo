package com.strider.taskmanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.setupActionBarWithNavController
import com.strider.taskmanager.R
import com.strider.taskmanager.databinding.ActivityMainBinding
import com.strider.taskmanager.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var listener: OnBackPressedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Custom)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)
//        viewModel.setUpDatabase()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun setOnBackPressedListener(listener: OnBackPressedListener) {
        this.listener = listener
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.taskDetailsFragment) {
            listener?.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }
}