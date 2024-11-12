package com.dicoding.asclepius.view.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.lokal.entity.CancerIdentify
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.dashboard.MainActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var cancerViewModel: CancerSaveUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

        val categories = intent.getParcelableArrayListExtra<ImageClassifierHelper.Category>(
            EXTRA_CATEGORIES
        )
        if (categories != null) {
            displayResults(categories)
        } else {
            binding.resultText.text = getString(R.string.no_prediction_available)
        }

        binding.imageBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayResults(categories: List<ImageClassifierHelper.Category>) {
        val sortedCategories = categories.sortedByDescending { it.score }
        val highestCategory = sortedCategories.firstOrNull()

        if (highestCategory != null) {
            val label = highestCategory.label
            val score = highestCategory.score

            // Simpan ke database
            val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
            val imageUriString = imageUri.toString()
            imageUriString.let { imageUrl ->
                val cancerIdentify = CancerIdentify(
                    image = imageUrl,
                    labels = label,
                    score = score
                )

                cancerViewModel = ViewModelProvider(this)[CancerSaveUpdateViewModel::class.java]
                cancerViewModel.insert(cancerIdentify)
            }

            binding.conclusion.text = "$label"

            val resultString = buildString {
                sortedCategories
                    .take(10)
                    .forEach { category ->
                        append("${category.label}: ${String.format("%.2f%%", category.score * 100)}\n")
                    }
            }
            binding.resultText.text = resultString

        } else {
            Log.e("ResultActivity", "No valid category found")
            binding.conclusion.text = getString(R.string.no_prediction_available)
            binding.resultText.text = ""
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_CATEGORIES = "extra_categories"
    }
}