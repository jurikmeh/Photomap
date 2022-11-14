package com.yurykasper.photomap.main.editPhotoDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import com.yurykasper.photomap.R
import com.yurykasper.photomap.databinding.FragmentEditPhotoDetailsBinding

class EditPhotoDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditPhotoDetailsBinding
    private val args: EditPhotoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPhotoDetailsBinding.inflate(inflater, container, false)

        val list = listOf("iOS", "Android", "Flutter")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_type_item, list)
        binding.typeDropDownInputEditText.setAdapter(adapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        val photo = args.photo
        if (photo != null) {
            binding.photoTitleInputEditText.setText(photo.title)
            binding.photoDescriptionInputEditText.setText(photo.description)
            binding.typeDropDownInputEditText.setText(photo.category.title)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditPhotoDetailsFragment()
    }

}