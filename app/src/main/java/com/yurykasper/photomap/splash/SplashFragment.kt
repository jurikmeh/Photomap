package com.yurykasper.photomap.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.yurykasper.photomap.MainActivity
import com.yurykasper.photomap.databinding.FragmentSplashBinding

private val IS_AUTHORIZED_KEY = "isAuthorized"

class SplashFragment: Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchMainScreen(FirebaseAuth.getInstance().currentUser != null)
    }

    private fun launchMainScreen(isAuthorized: Boolean) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        intent.putExtra(IS_AUTHORIZED_KEY, isAuthorized)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }
}