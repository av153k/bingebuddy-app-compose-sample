package com.bingebuddy.app.data.local.repository

import com.bingebuddy.app.data.local.model.WatchlistItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchlistRepository @Inject constructor(
    private val watchlistItemDao: WatchlistItemDao
) {

    suspend fun insertWatchlistItem(item: WatchlistItem) {
        watchlistItemDao.insert(item)
    }

    suspend fun deleteWatchlistItem(item: WatchlistItem) {
        watchlistItemDao.delete(item)
    }

    fun observeWatchlist(): Flow<List<WatchlistItem>> {
        return watchlistItemDao.observeAll()
    }
}