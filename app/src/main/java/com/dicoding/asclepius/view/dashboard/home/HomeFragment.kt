package com.dicoding.asclepius.view.dashboard.home

import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.utils.getImageUri
import com.dicoding.asclepius.view.result.ResultActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val dogAdapter = DogAdapter()

    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        loadDummyData()
        setupBreedFilter()

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        return view
    }

    private fun setupRecyclerView() {
        binding.rvDogs.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = dogAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.right = 8.dpToPx()
                }
            })
        }
    }

    private fun loadDummyData() {
        dogAdapter.setData(DummyData.dogList)
    }

    private fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun setupBreedFilter() {
        val breedAdapter = DogBreedFilterAdapter()

        binding.rvBreeds.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = breedAdapter
            // Opsional: menghilangkan overscroll effect
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        breedAdapter.setData(DummyDogBreeds.breeds)
        breedAdapter.setOnBreedSelectedListener { breedName ->
            // Handle ketika breed dipilih
            Toast.makeText(context, "Selected: $breedName", Toast.LENGTH_SHORT).show()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
//            showImage()
            // Langsung proses gambar setelah dipilih dari galeri
            processSelectedImage(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

//    private fun showImage() {
//        currentImageUri?.let {
//            Log.d("Image URI", "showImage: $it")
//            binding.previewImageView.setImageURI(it)
//        }
//    }

    private fun processSelectedImage(uri: Uri) {
        binding.progressIndicator.visibility = View.VISIBLE
        Log.d("MainActivity", "Starting image analysis")

        val imageClassifierHelper = ImageClassifierHelper(context = requireContext())

        imageClassifierHelper.classifyStaticImage(uri) { imageUri, categories ->
            Log.d("MainActivity", "Analysis complete. Categories: ${categories.size}")
            requireActivity().runOnUiThread {
                Log.d("MainActivity", "Moving to result activity")
                moveToResult(imageUri, categories)
                binding.progressIndicator.visibility = View.GONE
            }
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(context = requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
//            showImage()
            // Langsung proses gambar setelah mengambil foto
            currentImageUri?.let { uri ->
                processSelectedImage(uri)
            }
        }
    }

    private fun moveToResult(imageUri: String, categories: List<ImageClassifierHelper.Category>) {
        val intent = Intent(requireContext(), ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, imageUri)
        intent.putExtra(ResultActivity.EXTRA_CATEGORIES, ArrayList(categories))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}