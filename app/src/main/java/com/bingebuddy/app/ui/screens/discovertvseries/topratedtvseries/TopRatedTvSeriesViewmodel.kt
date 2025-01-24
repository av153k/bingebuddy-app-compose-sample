package com.bingebuddy.app.ui.screens.discovertvseries.topratedtvseries


import com.bingebuddy.app.data.repository.TvSeriesRepository
import com.bingebuddy.app.model.DiscoverTvSeriesResultModel



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val TAG = "TopRatedTvSeriesViewmodel"

sealed interface TopRatedTvSeriesUiState {
    data class Success(val tvSeries: List<DiscoverTvSeriesResultModel>) : TopRatedTvSeriesUiState
    data object Error : TopRatedTvSeriesUiState
    data object Loading : TopRatedTvSeriesUiState
}


@HiltViewModel
class TopRatedTvSeriesViewmodel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository
) : ViewModel() {

    var uiState: TopRatedTvSeriesUiState by mutableStateOf(TopRatedTvSeriesUiState.Loading)
        private set

    init {
        getTopRatedTvSeries()
    }

    fun getTopRatedTvSeries() {
        viewModelScope.launch {
            uiState = TopRatedTvSeriesUiState.Loading
            uiState = try {
                val discoverResult = tvSeriesRepository.getTopRatedTvSeries()
                TopRatedTvSeriesUiState.Success(
                    tvSeries = discoverResult.results ?: listOf()
                )
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                TopRatedTvSeriesUiState.Error
            } catch (e: HttpException) {
                Timber.tag(TAG).e(e)
                TopRatedTvSeriesUiState.Error
            }
        }
    }


}