package com.yurykasper.photomap.main.editPhotoDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yurykasper.photomap.R
import com.yurykasper.photomap.databinding.FragmentEditPhotoDetailsBinding
import com.yurykasper.photomap.extensions.afterTextChanged
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class EditPhotoDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditPhotoDetailsBinding
    private lateinit var viewModel: EditPhotoDetailsViewModel
    private val disposables = CompositeDisposable()
    private val args: EditPhotoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPhotoDetailsBinding.inflate(inflater, container, false)
        viewModel = EditPhotoDetailsViewModel(args.photo)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupBindings()
    }

    private fun setupView() {
        val photo = args.photo

        if (photo != null) {
            binding.photoTitleInputEditText.setText(photo.title)
            binding.photoDescriptionInputEditText.setText(photo.description)
            binding.typeDropDownInputEditText.setText(photo.category.title)
        }
    }

    private fun setupBindings() {
        binding.photoTitleInputEditText.afterTextChanged { viewModel.inputs.titleChanged(it) }
        binding.photoDescriptionInputEditText.afterTextChanged { viewModel.inputs.descriptionChanged(it) }
        binding.typeDropDownInputEditText.setOnItemClickListener { _, _, i, _ -> viewModel.inputs.categoryChanged(i) }

        viewModel.outputs.categories
            .subscribeBy { categoryList ->
                val list = categoryList.map { it.title }
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_type_item, list)
                binding.typeDropDownInputEditText.setAdapter(adapter)
            }.addTo(disposables)

        viewModel.outputs.changeState
            .subscribeBy { state ->
                when (state) {
                    PhotoSaveState.CREATE -> { findNavController().popBackStack() }
                    PhotoSaveState.EDIT -> { Toast.makeText(requireContext(), "Saved changes", Toast.LENGTH_LONG).show() }
                    PhotoSaveState.NONE -> {}
                }
            }.addTo(disposables)

        viewModel.outputs.saveButtonEnabled
            .subscribeBy { isEnabled -> binding.saveButton.isEnabled = isEnabled }
            .addTo(disposables)

        binding.saveButton.setOnClickListener { viewModel.saveChanges() }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditPhotoDetailsFragment()
    }

}