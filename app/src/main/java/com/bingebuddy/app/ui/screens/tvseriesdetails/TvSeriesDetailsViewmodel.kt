package com.bingebuddy.app.ui.screens.tvseriesdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.bingebuddy.app.data.repository.TvSeriesRepository
import com.bingebuddy.app.model.TvSeriesDetailsModel
import com.bingebuddy.app.ui.screens.discovertvseries.airingtodaytvseries.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


sealed interface TvSeriesDetailUiState {
    data class Success(val tvSeriesDetails: TvSeriesDetailsModel) : TvSeriesDetailUiState
    data class Error(val tvSeriesId: String?) : TvSeriesDetailUiState
    data object Loading : TvSeriesDetailUiState
}


@HiltViewModel
class TvSeriesDetailsViewmodel @Inject constructor(
    private val repository: TvSeriesRepository,
    savedStateHandle: SavedStateHandle,

    ): ViewModel() {

    var uiState: TvSeriesDetailUiState by mutableStateOf(TvSeriesDetailUiState.Loading)
        private set

    init {
        val movieId = savedStateHandle.get<String>("tvSeriesId")
        if (movieId != null) {
            getTvSeriesDetails(movieId)
        } else {
            uiState = TvSeriesDetailUiState.Error(null)
        }
    }

    fun getTvSeriesDetails(tvSeriesId: String) {
        viewModelScope.launch {
            uiState = TvSeriesDetailUiState.Loading
            uiState = try {
                val result = repository.getTvSeriesDetails(tvSeriesId)
                TvSeriesDetailUiState.Success(
                    tvSeriesDetails = result
                )
            } catch (e: IOException) {
                Timber.tag(TAG).e(e)
                TvSeriesDetailUiState.Error(tvSeriesId)
            } catch (e: HttpException) {
                Timber.tag(TAG).e(e)
                TvSeriesDetailUiState.Error(tvSeriesId)
            }
        }
    }
}