package com.bingebuddy.app.ui.screens.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bingebuddy.app.data.local.model.WatchlistItem
import com.bingebuddy.app.data.local.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewmodel @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    val allData: LiveData<List<WatchlistItem>> = watchlistRepository.getAllWatchlistItems().asLiveData()


    suspend fun insertMoviesIntoWatchlist(tmdbId: Long, title: String ) {
        val item = WatchlistItem(tmdbId = tmdbId, title = title, type = "movie")
        viewModelScope.launch {
            watchlistRepository.insertWatchlistItem(item)
        }
    }


}