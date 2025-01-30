package com.bingebuddy.app.ui.screens.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bingebuddy.app.data.local.model.WatchlistItem
import com.bingebuddy.app.data.local.repository.WatchlistRepository
import com.bingebuddy.app.utils.Async
import com.bingebuddy.app.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface WatchlistUiState {
    data class Success(val watchlistItems: List<WatchlistItem>) : WatchlistUiState
    data class Error(val errorMessage: String) : WatchlistUiState
    data object Loading : WatchlistUiState
}


@HiltViewModel
class WatchlistViewmodel @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    val allData: StateFlow<WatchlistUiState> =
        watchlistRepository.observeWatchlist().map {
            Async.Success(it)
        }.catch<Async<List<WatchlistItem>>> {
            emit(Async.Error("Unable to load watchlist"))
        }.map { itemsAsync ->
            produceWatchlistUiState(itemsAsync)
        }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = WatchlistUiState.Loading
            )


    fun insertIntoWatchlist(item: WatchlistItem) {
        viewModelScope.launch {
            watchlistRepository.insertWatchlistItem(item)
        }
    }


    fun deleteFromWatchlist(item: WatchlistItem) {
        viewModelScope.launch {
            watchlistRepository.deleteWatchlistItem(item)
        }
    }


    private fun produceWatchlistUiState(watchlistItemsAsync: Async<List<WatchlistItem>>) =
        when (watchlistItemsAsync) {
            Async.Loading -> {
                WatchlistUiState.Loading
            }

            is Async.Error -> {
                WatchlistUiState.Error(watchlistItemsAsync.errorMessage)
            }

            is Async.Success -> {
                WatchlistUiState.Success(watchlistItemsAsync.data)
            }
        }


}