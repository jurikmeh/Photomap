package com.yurykasper.photomap.main.photoDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.yurykasper.photomap.databinding.FragmentPhotoDetailsBinding
import com.yurykasper.photomap.models.photo.PhotoDVO

class PhotoDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailsBinding
    private val args: PhotoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PhotoDetailsFragment()
    }

    private fun setupView() {
        val photo = args.photo
        binding.photoNameLabel.text = photo.title
        binding.photoDescriptionLabel.text= photo.description
        binding.photoAuthorLabel.text = "${photo.author.firstname} ${photo.author.lastname}"
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        if (photo.author.id == userId) {
            binding.editPhotoButton.visibility = View.VISIBLE
        }

        binding.editPhotoButton.setOnClickListener {
            val direction = PhotoDetailsFragmentDirections.actionPhotoDetailsFragmentToEditPhotoDetailsFragment(args.photo)
            findNavController().navigate(direction)
        }
    }
}