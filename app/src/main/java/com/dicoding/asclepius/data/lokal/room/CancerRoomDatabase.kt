package com.dicoding.asclepius.data.lokal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.lokal.entity.CancerIdentify

@Database(entities = [CancerIdentify::class], version = 4)
abstract class CancerRoomDatabase : RoomDatabase() {
    abstract fun cancerDao(): CancerDao

    companion object {
        @Volatile
        private var INSTANCE: CancerRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CancerRoomDatabase {
            if (INSTANCE == null) {
                synchronized(CancerRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CancerRoomDatabase::class.java, "cancer_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as CancerRoomDatabase
        }
    }
}