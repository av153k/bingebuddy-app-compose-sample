package com.bingebuddy.app.ui.screens.discovertvseries.topratedtvseries


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

const val TAG = "TopRatedTvSeriesViewmodel"

sealed interface TopRatedTvSeriesUiState {
    data class Success(val tvSeriesList: List<DiscoverTvSeriesResultModel>) : TopRatedTvSeriesUiState
    data class Error(val message: String) : TopRatedTvSeriesUiState
    data object Loading : TopRatedTvSeriesUiState
}


@HiltViewModel
class TopRatedTvSeriesViewmodel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: TopRatedTvSeriesUiState by mutableStateOf(TopRatedTvSeriesUiState.Loading)
        private set

    init {
        getTopRatedTvSeries()
    }

    fun getTopRatedTvSeries() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = TopRatedTvSeriesUiState.Loading
                val result = tvSeriesRepository.getTopRatedTV(includeAdult = it)
                uiState = when (result) {
                    is AsyncResult.Success -> TopRatedTvSeriesUiState.Success(
                        result.data.results!!
                    )
                    is AsyncResult.Failure -> TopRatedTvSeriesUiState.Error(result.exception.message ?: "Something went wrong!!")
                }

            }
        }
    }


}