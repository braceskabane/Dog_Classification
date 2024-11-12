package com.dicoding.asclepius.view.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.lokal.entity.CancerIdentify
import com.dicoding.asclepius.data.repository.CancerRepository
import kotlinx.coroutines.launch

class CancerSaveUpdateViewModel(application: Application) : AndroidViewModel(application){
    private val mCancerRepository: CancerRepository = CancerRepository(application)

    fun insert(user: CancerIdentify) {
        viewModelScope.launch {
            mCancerRepository.insert(user)
        }
    }
}