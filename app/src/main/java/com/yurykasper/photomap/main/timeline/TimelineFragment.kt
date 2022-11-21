package com.yurykasper.photomap.main.timeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yurykasper.photomap.databinding.FragmentTimelineBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class TimelineFragment : Fragment() {

    private lateinit var binding: FragmentTimelineBinding
    private lateinit var adapter: TimelineAdapter
    private lateinit var viewModel: TimelineViewModel
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimelineBinding.inflate(inflater, container, false)
        viewModel = TimelineViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupBindings()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TimelineFragment()
    }

    private fun setupRecyclerView() = with(binding) {
        adapter = TimelineAdapter(object: RecyclerOnTouchListener {
            override fun onPhotoDetails(photoId: String) {
                val direction = TimelineFragmentDirections.actionTimelineFragmentToPhotoDetailsFragment(photoId)
                findNavController().navigate(direction)
            }
        })

        timelineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        timelineRecyclerView.adapter = adapter
    }

    private fun setupBindings() {
        viewModel.photosDVOList.subscribeBy {
            adapter.refresh(it)
        }.addTo(disposables)

        binding.addButton.setOnClickListener {
            val direction = TimelineFragmentDirections.actionTimelineFragmentToEditPhotoDetailsFragment(null)
            findNavController().navigate(direction)
        }
    }
}