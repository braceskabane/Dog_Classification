package com.dicoding.asclepius.data.lokal.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "canceridentify")
@Parcelize
data class CancerIdentify(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "label")
    val labels: String,
    @ColumnInfo(name = "score")
    val score: Float
): Parcelable


