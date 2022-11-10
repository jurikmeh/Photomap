package com.yurykasper.photomap.main.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yurykasper.photomap.databinding.ItemTimelineBinding
import com.yurykasper.photomap.models.PhotoDTO

interface RecyclerOnTouchListener {
    fun onPhotoDetails(photo: PhotoDTO)
}

class TimelineAdapter(
    private val recyclerListener: RecyclerOnTouchListener
): RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>(), View.OnClickListener {

    private var timelineItems: List<PhotoDTO> = emptyList()

    fun refresh(list: List<PhotoDTO>) {
        timelineItems = list
        notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        val photoDTO = v.tag as PhotoDTO
        recyclerListener.onPhotoDetails(photoDTO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimelineBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return TimelineViewHolder(binding)
    }

    override fun getItemCount(): Int = timelineItems.size

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val item = timelineItems[position]
        with(holder.binding) {
            holder.itemView.tag = item
            categoryLabel.text = item.category
            nameLabel.text = item.name
            descriptionLabel.text = item.description
            authorLabel.text = item.authorId
        }
    }

    class TimelineViewHolder(
        val binding: ItemTimelineBinding
    ): RecyclerView.ViewHolder(binding.root)

}