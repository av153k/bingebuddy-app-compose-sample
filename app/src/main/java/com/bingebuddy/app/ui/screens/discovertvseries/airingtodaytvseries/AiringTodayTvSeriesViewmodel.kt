package com.bingebuddy.app.ui.screens.discovertvseries.airingtodaytvseries


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

const val TAG = "AiringTodayTvSeriesViewmodel"

sealed interface AiringTodayTvSeriesUiState {
    data class Success(val tvSeries: List<DiscoverTvSeriesResultModel>) : AiringTodayTvSeriesUiState
    data object Error : AiringTodayTvSeriesUiState
    data object Loading : AiringTodayTvSeriesUiState
}


@HiltViewModel
class AiringTodayTvSeriesViewmodel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository
) : ViewModel() {

    var uiState: AiringTodayTvSeriesUiState by mutableStateOf(AiringTodayTvSeriesUiState.Loading)
        private set

    init {
        getAiringTodayTvSeries()
    }

    fun getAiringTodayTvSeries() {
        viewModelScope.launch {
            uiState = AiringTodayTvSeriesUiState.Loading
            uiState = try {
                val discoverResult = tvSeriesRepository.getAiringTodayTvSeries()
                AiringTodayTvSeriesUiState.Success(
                    tvSeries = discoverResult.results ?: listOf()
                )
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                AiringTodayTvSeriesUiState.Error
            } catch (e: HttpException) {
                Timber.tag(TAG).e(e)
                AiringTodayTvSeriesUiState.Error
            }
        }
    }


}