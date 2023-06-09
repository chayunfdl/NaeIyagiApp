package com.chayun.naeiyagiapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chayun.naeiyagiapp.data.response.ListIyagiItem
import com.chayun.naeiyagiapp.databinding.ItemIyagiBinding
import com.chayun.naeiyagiapp.ui.detailiyagi.DetailIyagiActivity

class IyagiListAdapter : PagingDataAdapter<ListIyagiItem, IyagiListAdapter.MyViewHolder>(
    DIFF_CALLBACK
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemIyagiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemIyagiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:ListIyagiItem){
            binding.apply{
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .into(binding.ivItemPhoto)
                binding.tvItemName.text = data.name
                binding.tvDescription.text = data.description
            }

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailIyagiActivity::class.java)
                intent.putExtra("DATA", data)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivItemPhoto, "profile"),
                        Pair(binding.tvItemName, "name"),
                        Pair(binding.tvDescription, "description"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListIyagiItem>() {
            override fun areItemsTheSame(oldItem: ListIyagiItem, newItem: ListIyagiItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListIyagiItem, newItem: ListIyagiItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}