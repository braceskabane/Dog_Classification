package com.dicoding.asclepius.view.dashboard.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.lokal.entity.CancerIdentify
import com.dicoding.asclepius.data.repository.CancerRepository

class SaveIdentifyViewModel(private val repository: CancerRepository): ViewModel() {
    private val _cancers = MutableLiveData<List<CancerIdentify>>()
    val cancers: LiveData<List<CancerIdentify>> = _cancers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllCancers() {
        _isLoading.value = true
        repository.getAllCancers().observeForever { userList ->
            _cancers.value = userList
            _isLoading.value = false
        }
    }
    fun deleteAllCancers() {
        _isLoading.value = true
        repository.deleteAllCancers {
            getAllCancers()
        }
    }
}