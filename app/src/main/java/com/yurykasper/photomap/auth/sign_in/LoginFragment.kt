package com.yurykasper.photomap.auth.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.yurykasper.photomap.R
import com.yurykasper.photomap.databinding.FragmentLoginBinding
import com.yurykasper.photomap.extensions.afterTextChanged
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

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

        binding.emailTextInputEditText.afterTextChanged { text -> viewModel.inputs.emailChanged(text) }
        binding.passwordTextInputEditText.afterTextChanged { text -> viewModel.inputs.passwordChanged(text) }

        binding.loginButton.setOnClickListener {
            viewModel.inputs.loginButtonPressed()
        }

        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        viewModel.outputs.loginButtonEnabled
            .subscribeBy { isEnabled ->
                binding.loginButton.isEnabled = isEnabled
            }.addTo(disposables)

        viewModel.outputs.showMainFragment
            .subscribeBy {
                if (it) {
                    findNavController().navigate(R.id.action_loginFragment_to_tabsFragment)
                } else {
                    Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }.addTo(disposables)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}