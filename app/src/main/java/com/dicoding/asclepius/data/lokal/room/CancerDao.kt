package com.dicoding.asclepius.data.lokal.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
//import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
//import androidx.room.Update
import com.dicoding.asclepius.data.lokal.entity.CancerIdentify

@Dao
interface CancerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cancerIdentify: CancerIdentify)

    @Query("SELECT * FROM canceridentify")
    fun getAllCancers(): LiveData<List<CancerIdentify>>

    @Query("DELETE FROM canceridentify")
    fun deleteAll()
}