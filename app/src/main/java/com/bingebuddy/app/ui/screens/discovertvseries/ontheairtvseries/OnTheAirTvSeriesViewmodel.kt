package com.bingebuddy.app.ui.screens.discovertvseries.ontheairtvseries


import android.os.Build
import androidx.annotation.RequiresApi
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


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OnTheAirTvSeriesViewmodel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: OnTheAirTvSeriesUiState by mutableStateOf(OnTheAirTvSeriesUiState.Loading)
        private set

    init {
        getOnTheAirTvSeries()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getOnTheAirTvSeries() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = OnTheAirTvSeriesUiState.Loading
                uiState = try {
                    val discoverResult = tvSeriesRepository.getOnTheAirTV(includeAdult = it)
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


}