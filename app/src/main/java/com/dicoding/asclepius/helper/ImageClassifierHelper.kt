package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.dicoding.asclepius.ml.Model
import kotlinx.parcelize.Parcelize
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class ImageClassifierHelper(
    private val context: Context,
    private val classifierListener: ClassifierListener? = null,
    private var threshold: Float = 0.0f,
    private var maxResults: Int = 10,
) {
    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: List<Classification>?)
    }

    data class Classification(
        val label: String,
        val score: Float
    )

    @Parcelize
    data class Category(val label: String, val score: Float) : Parcelable

    companion object {
        private const val TAG = "ImageClassifierHelper"
        private const val IMAGE_SIZE = 224

        // Mapping dari index ke label original
        private val INDEX_TO_LABEL = mapOf(
            0 to "n02085620-Chihuahua",
            1 to "n02085782-Japanese_spaniel",
            2 to "n02085936-Maltese_dog",
            3 to "n02086079-Pekinese",
            4 to "n02086240-Shih-Tzu",
            5 to "n02086646-Blenheim_spaniel",
            6 to "n02086910-papillon",
            7 to "n02087046-toy_terrier",
            8 to "n02087394-Rhodesian_ridgeback",
            9 to "n02088094-Afghan_hound"
        )

        // Mapping dari label original ke display name
        private val LABEL_DISPLAY_NAMES = mapOf(
            "n02085620-Chihuahua" to "Chihuahua",
            "n02085782-Japanese_spaniel" to "Japanese Spaniel",
            "n02085936-Maltese_dog" to "Maltese Dog",
            "n02086079-Pekinese" to "Pekinese",
            "n02086240-Shih-Tzu" to "Shih-Tzu",
            "n02086646-Blenheim_spaniel" to "Blenheim Spaniel",
            "n02086910-papillon" to "Papillon",
            "n02087046-toy_terrier" to "Toy Terrier",
            "n02087394-Rhodesian_ridgeback" to "Rhodesian Ridgeback",
            "n02088094-Afghan_hound" to "Afghan Hound"
        )
    }

    fun classifyStaticImage(uri: Uri, onAnalysisComplete: (imageUri: String, categories: List<Category>) -> Unit) {
        try {
            // Load bitmap
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                    decoder.isMutableRequired = true
                }.copy(Bitmap.Config.ARGB_8888, true)
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    .copy(Bitmap.Config.ARGB_8888, true)
            }

            // Create TensorImage dan process
            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)

            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(IMAGE_SIZE, IMAGE_SIZE, ResizeOp.ResizeMethod.BILINEAR))
                .add(NormalizeOp(127.5f, 127.5f))
                .build()

            val processedImage = imageProcessor.process(tensorImage)

            // Run inference
            val model = Model.newInstance(context)
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, IMAGE_SIZE, IMAGE_SIZE, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(processedImage.buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val probs = outputFeature0.floatArray

            // Proses semua hasil tanpa filter
            val categories = probs.take(10).mapIndexed { index, score ->
                val originalLabel = INDEX_TO_LABEL[index] ?: "Unknown"
                val displayName = LABEL_DISPLAY_NAMES[originalLabel] ?: originalLabel
                Category(displayName, score)
            }

            // Cleanup
            model.close()
            bitmap.recycle()

            // Debug log untuk memastikan jumlah prediksi
            Log.d(TAG, "Number of predictions: ${categories.size}")
            categories.forEach {
                Log.d(TAG, "${it.label}: ${it.score * 100}%")
            }

            // Kirim hasil
            onAnalysisComplete(uri.toString(), categories)

        } catch (e: Exception) {
            Log.e(TAG, "Error analyzing image: ${e.message}")
            e.printStackTrace() // Tambahkan stack trace untuk debugging
            showToast("Error analyzing image: ${e.message}")
        }
    }

    fun close() {
        // Clean up resources if needed
    }

    fun setThreshold(threshold: Float) {
        this.threshold = threshold
    }

    fun setMaxResults(maxResults: Int) {
        this.maxResults = maxResults
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}