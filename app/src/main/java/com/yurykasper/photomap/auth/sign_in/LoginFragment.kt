package com.yurykasper.photomap.auth.sign_in

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.yurykasper.photomap.R
import com.yurykasper.photomap.databinding.FragmentLoginBinding
import com.yurykasper.photomap.extensions.afterTextChanged
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModelType
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = LoginViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.usernameTextInputEditText.afterTextChanged { text -> viewModel.usernameChanged(text) }
        binding.passwordTextInputEditText.afterTextChanged { text -> viewModel.passwordChanged(text) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}