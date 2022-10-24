package com.yurykasper.photomap.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.yurykasper.photomap.R

fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.loginContainerView) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}