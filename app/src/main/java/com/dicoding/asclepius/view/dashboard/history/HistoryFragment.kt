package com.dicoding.asclepius.view.dashboard.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.utils.CancerAdapter
import com.dicoding.asclepius.view.dashboard.MainActivity
import com.dicoding.asclepius.view.factory.SaveIdentifyViewModelFactory


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar
    private lateinit var saveIdentifyViewModel: SaveIdentifyViewModel
    private lateinit var adapter: CancerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Gunakan ActivitySaveIdentifyBinding di Fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        progressBar = binding.progressBar
        loadDataFromApi()

        // Setup adapter dan RecyclerView
        adapter = CancerAdapter()
        binding.usersRecycleView2.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRecycleView2.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )
        binding.usersRecycleView2.adapter = adapter

        // Inisialisasi ViewModel
        val factory = SaveIdentifyViewModelFactory(requireActivity().application)
        saveIdentifyViewModel = ViewModelProvider(this, factory)[SaveIdentifyViewModel::class.java]

        // Observe loading state
        saveIdentifyViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe data changes and set to adapter
        saveIdentifyViewModel.cancers.observe(viewLifecycleOwner) { cancers ->
            adapter.submitList(cancers)
        }

        saveIdentifyViewModel.getAllCancers()

        // Set up button click listeners
        binding.ivBack.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.ivDelete.setOnClickListener {
            saveIdentifyViewModel.deleteAllCancers()
        }

        return view
    }

    private fun loadDataFromApi() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}