package com.yurykasper.photomap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBarWithNavController(findNavController(R.id.loginContainerView))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.loginContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}