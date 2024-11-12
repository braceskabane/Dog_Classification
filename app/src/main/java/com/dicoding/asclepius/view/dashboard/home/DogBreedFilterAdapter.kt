package com.dicoding.asclepius.view.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.databinding.ItemLabelBinding

class DogBreedFilterAdapter : RecyclerView.Adapter<DogBreedFilterAdapter.BreedViewHolder>() {

    private val breeds = mutableListOf<DogBreedFilter>()
    private var selectedPosition = -1
    private var onBreedSelectedListener: ((String) -> Unit)? = null

    fun setData(newBreeds: List<DogBreedFilter>) {
        breeds.clear()
        breeds.addAll(newBreeds)
        selectedPosition = breeds.indexOfFirst { it.isSelected }
        notifyDataSetChanged()
    }

    fun setOnBreedSelectedListener(listener: (String) -> Unit) {
        onBreedSelectedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding = ItemLabelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(breeds[position])
    }

    override fun getItemCount() = breeds.size

    inner class BreedViewHolder(private val binding: ItemLabelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    updateSelection(position)
                }
            }
        }

        fun bind(breed: DogBreedFilter) {
            binding.tvBreed.apply {
                text = breed.name
                isSelected = adapterPosition == selectedPosition
            }
        }
    }

    private fun updateSelection(position: Int) {
        if (selectedPosition != position) {
            val oldPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)
            onBreedSelectedListener?.invoke(breeds[position].name)
        }
    }
}