package com.pyruby.communities.thing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pyruby.communities.databinding.ListItemThingBinding
import com.pyruby.communities.home.CommunityViewModel
import com.pyruby.fragment.Thing


class ThingAdapter(val viewmodel: CommunityViewModel) : ListAdapter<Thing, RecyclerView.ViewHolder>(ThingDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ThingViewHolder(ListItemThingBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewmodel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ThingViewHolder).bind(getItem(position))
    }

    class ThingViewHolder(
        private val binding: ListItemThingBinding,
        private val viewmodel: CommunityViewModel
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Thing) {
            binding.apply {
                thing = item
                executePendingBindings()
            }
            binding.imageView2.setOnClickListener {
                viewmodel.removeThing(item)
            }
        }

    }
}

private class ThingDiffCallback : DiffUtil.ItemCallback<Thing>() {
    override fun areItemsTheSame(oldItem: Thing, newItem: Thing): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Thing, newItem: Thing): Boolean {
        return oldItem == newItem
    }

}