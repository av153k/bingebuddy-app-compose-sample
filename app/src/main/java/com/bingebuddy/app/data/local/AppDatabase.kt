package com.bingebuddy.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bingebuddy.app.data.local.model.WatchlistItem
import com.bingebuddy.app.data.local.repository.WatchlistItemDao

@Database(entities = [WatchlistItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun watchlistItemDao(): WatchlistItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}