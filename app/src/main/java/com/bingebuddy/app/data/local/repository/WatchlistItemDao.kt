package com.bingebuddy.app.data.local.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bingebuddy.app.data.local.model.WatchlistItem
import kotlinx.coroutines.flow.Flow


@Dao
interface WatchlistItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: WatchlistItem)

    @Delete
    suspend fun delete(item: WatchlistItem)

    @Query("SELECT * FROM watchlist_items ORDER BY created_at DESC")
    fun getAll(): Flow<List<WatchlistItem>>

}