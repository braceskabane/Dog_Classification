package com.dicoding.asclepius.view.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.databinding.ItemDogBinding

class DogAdapter : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    private val dogList = mutableListOf<DogData>()

    fun setData(newList: List<DogData>) {
        dogList.clear()
        dogList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = ItemDogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(dogList[position])
    }

    override fun getItemCount() = dogList.size

    inner class DogViewHolder(private val binding: ItemDogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DogData) {
            with(binding) {
                // Load image dari drawable
                imageDestination.setImageResource(data.imageUrl)
                // Atau jika ingin menggunakan Glide
                /* Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .transform(CenterCrop())
                    .into(imageDestination) */

                tvCountry.text = data.country
                tvClassification.text = data.classification
                textRating.text = data.rating.toString()
                textReviews.text = "${data.reviews} reviews"

                // Handle favorite
                buttonFavorite.setOnClickListener {
                    // Handle favorite click
                }

                // Handle see more
                buttonSeeMore.setOnClickListener {
                    // Handle see more click
                }
            }
        }
    }
}