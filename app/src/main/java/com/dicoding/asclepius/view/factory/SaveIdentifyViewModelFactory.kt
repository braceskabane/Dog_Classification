package com.dicoding.asclepius.view.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.CancerRepository
import com.dicoding.asclepius.view.dashboard.history.SaveIdentifyViewModel

class SaveIdentifyViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaveIdentifyViewModel::class.java)) {
            val repository = CancerRepository(application)
            @Suppress("UNCHECKED_CAST")
            return SaveIdentifyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}