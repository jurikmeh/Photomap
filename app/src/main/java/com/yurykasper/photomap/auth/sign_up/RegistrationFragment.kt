package com.yurykasper.photomap.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.yurykasper.photomap.R
import com.yurykasper.photomap.databinding.FragmentRegistrationBinding
import com.yurykasper.photomap.extensions.afterTextChanged
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: RegistrationViewModelType
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        viewModel = RegistrationViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailTextInputEditText.afterTextChanged { viewModel.inputs.emailChanged(it) }
        binding.passwordTextInputEditText.afterTextChanged { viewModel.inputs.passwordChanged(it) }
        binding.firstnameTextInputEditText.afterTextChanged { viewModel.inputs.firstNameChanged(it) }
        binding.lastnameTextInputEditText.afterTextChanged { viewModel.inputs.lastNameChanged(it) }
        binding.phoneTextInputEditText.afterTextChanged { viewModel.inputs.phoneChanged(it) }

        binding.registrationButton.setOnClickListener { viewModel.inputs.registrationButtonPressed() }

        viewModel.outputs.registrationButtonEnabled
            .subscribeBy { isEnabled ->
                binding.registrationButton.isEnabled = isEnabled
            }.addTo(disposables)

        viewModel.outputs.showMainFragment
            .subscribeBy {
                if (it) {
                    findNavController().navigate(R.id.action_registrationFragment_to_tabsFragment)
                } else {
                    Toast.makeText(requireContext(), "Invalid input data", Toast.LENGTH_SHORT).show()
                }
            }
            .addTo(disposables)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegistrationFragment()
    }
}