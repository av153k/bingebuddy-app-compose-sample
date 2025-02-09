package com.bingebuddy.app.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bingebuddy.app.AppStateManager
import com.bingebuddy.app.data.network.repository.SearchRepository
import com.bingebuddy.app.data.network.model.SearchResultModel
import com.bingebuddy.app.utils.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


const val TAG = "SearchViewmodel"

sealed interface SearchUiState {
    data class Success(
        val searchResults: List<SearchResultModel>,
        val isLoadingMore: Boolean = false
    ) : SearchUiState

    data class Error(val message: String = "Something went wrong") : SearchUiState
    data object Initial : SearchUiState
    data object Loading : SearchUiState
}

@FlowPreview
@HiltViewModel
class SearchViewmodel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val appStateManager: AppStateManager,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery

    var uiState: SearchUiState by mutableStateOf(SearchUiState.Initial)

    init {
        observeSearchQuery()
    }


    private fun observeSearchQuery() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect { includeAdult ->
                _searchQuery.debounce(300).filter { it.isNotBlank() }.distinctUntilChanged()
                    .collect {
                        uiState =
                            if (uiState is SearchUiState.Success && (uiState as SearchUiState.Success).searchResults.isNotEmpty()) SearchUiState.Success(
                                searchResults = (uiState as SearchUiState.Success).searchResults,
                                isLoadingMore = true
                            ) else SearchUiState.Loading
                        getSearchResults(_searchQuery.value, includeAdult)
                    }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }


    private suspend fun getSearchResults(query: String, includeAdult: Boolean) {

            val searchResult = searchRepository.searchAll(query, includeAdult = includeAdult)
            when (searchResult) {
                is AsyncResult.Success -> {
                    uiState = SearchUiState.Success(searchResults = searchResult.data.results!!)

                }
                is AsyncResult.Failure -> {
                    uiState = SearchUiState.Error(searchResult.exception.message ?: "Something went wrong")
                }
                else -> {}
            }

    }


}