package com.yurykasper.photomap.main.timeline

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yurykasper.photomap.R
import com.yurykasper.photomap.databinding.ItemTimelineBinding
import com.yurykasper.photomap.models.PhotoDTO

//class TimelineAdapter: RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {
//
//    var timelineItems: List<PhotoDTO> = emptyList()
//    @SuppressLint("NotifyDataSetChanged")
//    set(newValue) {
//        field = newValue
//        this.notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemTimelineBinding.inflate(inflater, parent, false)
//        return TimelineViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int = timelineItems.size
//
//
//    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
//        val item = timelineItems[position]
//        with(holder.binding) {
//            categoryLabel.text = item.category
//            nameLabel.text = item.name
//            descriptionLabel.text = item.description
//            authorLabel.text = item.authorId
//        }
//    }
//
//    class TimelineViewHolder(
//        val binding: ItemTimelineBinding
//    ): RecyclerView.ViewHolder(binding.root)
//
//}

class TimelineAdapter: ListAdapter<PhotoDTO, TimelineAdapter.TimelineViewHolder>(Comparator()) {

    class TimelineViewHolder(val binding: ItemTimelineBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PhotoDTO) = with(binding) {
            categoryLabel.text = item.category
            categoryLabel.setBackgroundColor(Color.parseColor("#4f7635"))
            nameLabel.text = item.name
            descriptionLabel.text = item.description
            authorLabel.text = item.authorId
        }
    }

    class Comparator : DiffUtil.ItemCallback<PhotoDTO>() {
        override fun areItemsTheSame(oldItem: PhotoDTO, newItem: PhotoDTO): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: PhotoDTO, newItem: PhotoDTO): Boolean {
            return false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimelineBinding.inflate(inflater, parent, false)
        return TimelineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
//        val item = timelineItems[position]
//        with(holder.binding) {
//            categoryLabel.text = item.category
//            nameLabel.text = item.name
//            descriptionLabel.text = item.description
//            authorLabel.text = item.authorId
//        }
        holder.bind(getItem(position))
    }
}