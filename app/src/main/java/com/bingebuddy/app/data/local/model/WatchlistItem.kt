package com.bingebuddy.app.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "watchlist_items", indices = [Index(value = ["tmdb_id"], unique = true)])
data class WatchlistItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "tmdb_id")
    val tmdbId: Long,

    val name: String,

    val type: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    val adult: Boolean,

    @ColumnInfo(name="release_date")
    val releaseDate: String?,

    @ColumnInfo(name="vote_average")
    val voteAverage: Double?,

    @ColumnInfo(name="vote_count")
    val voteCount: Long?,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = Date().time
)
