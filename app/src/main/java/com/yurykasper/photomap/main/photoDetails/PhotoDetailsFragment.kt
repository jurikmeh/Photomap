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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class PhotoDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailsBinding
    private lateinit var viewModel: PhotoDetailsViewModelType
    private val args: PhotoDetailsFragmentArgs by navArgs()
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        viewModel = PhotoDetailsViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.inputs.getPhotoDetails(args.photo.id)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PhotoDetailsFragment()
    }

    private fun setupView() {
        viewModel.outputs.photoDetails
            .subscribeBy { photo ->
                binding.photoNameLabel.text = photo.title
                binding.photoDescriptionLabel.text= photo.description
                binding.photoAuthorLabel.text = "${photo.author.firstname} ${photo.author.lastname}"
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                if (photo.author.uid == userId) {
                    binding.editPhotoButton.visibility = View.VISIBLE
                }
            }.addTo(disposables)

        binding.editPhotoButton.setOnClickListener {
            val direction = PhotoDetailsFragmentDirections.actionPhotoDetailsFragmentToEditPhotoDetailsFragment(args.photo)
            findNavController().navigate(direction)
        }
    }
}