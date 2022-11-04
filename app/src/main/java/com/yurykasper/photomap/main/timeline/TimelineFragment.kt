package com.yurykasper.photomap.main.timeline

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yurykasper.photomap.databinding.FragmentTimelineBinding
import com.yurykasper.photomap.models.PhotoDTO
import java.util.*

class TimelineFragment : Fragment() {

    private lateinit var binding: FragmentTimelineBinding
    private lateinit var adapter: TimelineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TimelineFragment()
    }

    private fun setupRecyclerView() = with(binding) {
        adapter = TimelineAdapter()
        timelineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        timelineRecyclerView.adapter = adapter
        val list = listOf(
            PhotoDTO(
                "cinema: Red Star",
                "default cinema",
                "Sight",
                Date(),
                "Id: Yury Kasper",
                emptyList(),
                Location("")
            )
        )
        adapter.refresh(list)
    }
}