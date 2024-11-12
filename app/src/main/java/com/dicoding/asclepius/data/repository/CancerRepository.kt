package com.dicoding.asclepius.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.lokal.entity.CancerIdentify
import com.dicoding.asclepius.data.lokal.room.CancerDao
import com.dicoding.asclepius.data.lokal.room.CancerRoomDatabase
import com.dicoding.asclepius.utils.AppExecutor

class CancerRepository(application: Application) {
    private val mCancerDao: CancerDao
    private val appExecutor: AppExecutor = AppExecutor()

    init {
        val db = CancerRoomDatabase.getDatabase(application)
        mCancerDao = db.cancerDao()
    }

    fun getAllCancers(): LiveData<List<CancerIdentify>> = mCancerDao.getAllCancers()

    fun insert(note: CancerIdentify) {
        appExecutor.diskIO.execute {
            mCancerDao.insert(note)
        }
    }

    fun deleteAllCancers(onComplete: () -> Unit) {
        appExecutor.diskIO.execute {
            mCancerDao.deleteAll()
            onComplete()
        }
    }
}