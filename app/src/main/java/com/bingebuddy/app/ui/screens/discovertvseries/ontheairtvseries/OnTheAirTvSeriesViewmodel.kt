package com.bingebuddy.app.ui.screens.discovertvseries.ontheairtvseries


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

const val TAG = "OnTheAirTvSeriesViewmodel"

sealed interface OnTheAirTvSeriesUiState {
    data class Success(val tvSeriesList: List<DiscoverTvSeriesResultModel>) : OnTheAirTvSeriesUiState
    data object Error : OnTheAirTvSeriesUiState
    data object Loading : OnTheAirTvSeriesUiState
}


@HiltViewModel
class OnTheAirTvSeriesViewmodel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository
) : ViewModel() {

    var uiState: OnTheAirTvSeriesUiState by mutableStateOf(OnTheAirTvSeriesUiState.Loading)
        private set

    init {
        getOnTheAirTvSeries()
    }

    fun getOnTheAirTvSeries() {
        viewModelScope.launch {
            uiState = OnTheAirTvSeriesUiState.Loading
            uiState = try {
                val discoverResult = tvSeriesRepository.getOnTheAirTvSeries()
                OnTheAirTvSeriesUiState.Success(
                    tvSeriesList = discoverResult.results ?: listOf()
                )
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                OnTheAirTvSeriesUiState.Error
            } catch (e: HttpException) {
                Timber.tag(TAG).e(e)
                OnTheAirTvSeriesUiState.Error
            }
        }
    }


}