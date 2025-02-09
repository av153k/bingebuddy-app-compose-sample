package com.bingebuddy.app.ui.screens.discovertvseries.trendingtvseries


import com.bingebuddy.app.data.network.repository.TvSeriesRepository
import com.bingebuddy.app.data.network.model.DiscoverTvSeriesResultModel



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.bingebuddy.app.AppStateManager
import com.bingebuddy.app.utils.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val TAG = "TrendingTvSeriesViewmodel"

sealed interface TrendingTvSeriesUiState {
    data class Success(val tvSeriesList: List<DiscoverTvSeriesResultModel>) : TrendingTvSeriesUiState
    data class Error(val message: String) : TrendingTvSeriesUiState
    data object Loading : TrendingTvSeriesUiState
}


@HiltViewModel
class TrendingTvSeriesViewmodel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: TrendingTvSeriesUiState by mutableStateOf(TrendingTvSeriesUiState.Loading)
        private set

    init {
        getTrendingTvSeries()
    }

    fun getTrendingTvSeries() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = TrendingTvSeriesUiState.Loading
                val result = tvSeriesRepository.getTrendingTVWeek(includeAdult = it)
                uiState = when (result) {
                    is AsyncResult.Success -> TrendingTvSeriesUiState.Success(
                        result.data.results!!
                    )
                    is AsyncResult.Failure -> TrendingTvSeriesUiState.Error(result.exception.message ?: "Something went wrong!!")

                }
            }
        }
    }


}