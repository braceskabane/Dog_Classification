package com.dicoding.asclepius.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.lokal.entity.CancerIdentify
import com.dicoding.asclepius.databinding.ItemSaveBinding
import com.dicoding.asclepius.view.result.ResultActivity
import com.squareup.picasso.Picasso

class CancerAdapter : RecyclerView.Adapter<CancerAdapter.MyViewHolder>() {

    private var items: List<Any> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSaveBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (val item = items[position]) {
            is CancerIdentify -> holder.bindSave(item)
            else -> throw IllegalArgumentException("Unsupported item type")
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<Any>) {
        val diffResult = DiffUtil.calculateDiff(ReviewDiffCallback(items, newItems))
        this.items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    private class ReviewDiffCallback(
        private val oldList: List<Any>,
        private val newList: List<Any>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return when {
                oldItem is CancerIdentify && newItem is CancerIdentify -> oldItem.image == newItem.image
                else -> false
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }

    inner class MyViewHolder(private val binding: ItemSaveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindSave(item: CancerIdentify){
            Picasso.get().load(item.image).into(binding.photoProfile)
            binding.textName.text = item.labels
            binding.idTextView.text = item.score.toString()
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, ResultActivity::class.java)
                intentDetail.putExtra("username", item.image)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }
}