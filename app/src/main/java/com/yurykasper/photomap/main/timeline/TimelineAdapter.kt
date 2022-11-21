package com.yurykasper.photomap.main.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yurykasper.photomap.databinding.ItemTimelineBinding
import com.yurykasper.photomap.models.photo.PhotoDVO

interface RecyclerOnTouchListener {
    fun onPhotoDetails(photoId: String)
}

class TimelineAdapter(
    private val recyclerListener: RecyclerOnTouchListener
): RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>(), View.OnClickListener {

    private var timelineItems: List<PhotoDVO> = emptyList()

    fun refresh(list: List<PhotoDVO>) {
        timelineItems = list
        notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        val photoDTO = v.tag as PhotoDVO
        recyclerListener.onPhotoDetails(photoDTO.id)
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
            categoryLabel.text = item.category.title
            nameLabel.text = item.title
            descriptionLabel.text = item.description
            authorLabel.text = "${item.author.firstname} ${item.author.lastname}"
        }
    }

    class TimelineViewHolder(
        val binding: ItemTimelineBinding
    ): RecyclerView.ViewHolder(binding.root)

}