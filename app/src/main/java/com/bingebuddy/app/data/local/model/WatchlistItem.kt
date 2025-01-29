package com.bingebuddy.app.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "watchlist_items")
data class WatchlistItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "tmdb_id")
    val tmdbId: Long,

    val title: String,

    val type: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
)
