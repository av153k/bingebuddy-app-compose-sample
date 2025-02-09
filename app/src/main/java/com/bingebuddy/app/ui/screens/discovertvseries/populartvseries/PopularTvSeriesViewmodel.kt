package com.bingebuddy.app.ui.screens.discovertvseries.populartvseries

import com.bingebuddy.app.data.network.repository.TvSeriesRepository
import com.bingebuddy.app.data.network.model.DiscoverTvSeriesResultModel



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.bingebuddy.app.AppStateManager
import com.bingebuddy.app.AppViewmodel
import com.bingebuddy.app.utils.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val TAG = "PopularTvSeriesViewmodel"

sealed interface PopularTvSeriesUiState {
    data class Success(val tvSeriesList: List<DiscoverTvSeriesResultModel>) : PopularTvSeriesUiState
    data class Error(val message: String) : PopularTvSeriesUiState
    data object Loading : PopularTvSeriesUiState
}


@HiltViewModel
class PopularTvSeriesViewmodel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: PopularTvSeriesUiState by mutableStateOf(PopularTvSeriesUiState.Loading)
        private set

    init {
        getPopularTvSeries()
    }

    fun getPopularTvSeries() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = PopularTvSeriesUiState.Loading
                val result = tvSeriesRepository.getPopularTV(includeAdult = it)
                uiState = when (result) {
                    is AsyncResult.Success -> PopularTvSeriesUiState.Success(
                        result.data.results!!
                    )
                    is AsyncResult.Failure -> PopularTvSeriesUiState.Error(result.exception.message ?: "Something went wrong!!")
                }
            }
        }
    }


}