package com.bingebuddy.app.ui.screens.discovertvseries.airingtodaytvseries


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
import com.bingebuddy.app.utils.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val TAG = "AiringTodayTvSeriesViewmodel"

sealed interface AiringTodayTvSeriesUiState {
    data class Success(val tvSeriesList: List<DiscoverTvSeriesResultModel>) : AiringTodayTvSeriesUiState
    data class Error(val message: String) : AiringTodayTvSeriesUiState
    data object Loading : AiringTodayTvSeriesUiState
}


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AiringTodayTvSeriesViewmodel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository,
    private val appStateManager: AppStateManager
) : ViewModel() {

    var uiState: AiringTodayTvSeriesUiState by mutableStateOf(AiringTodayTvSeriesUiState.Loading)
        private set

    init {
        getAiringTodayTvSeries()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAiringTodayTvSeries() {
        viewModelScope.launch {
            appStateManager.allowExplicitContents.collect {
                uiState = AiringTodayTvSeriesUiState.Loading
                val result = tvSeriesRepository.getAiringTodayTV(includeAdult = it)
                uiState = when (result) {
                    is AsyncResult.Success -> AiringTodayTvSeriesUiState.Success(
                        result.data.results!!
                    )

                    is AsyncResult.Failure -> AiringTodayTvSeriesUiState.Error(result.exception.message ?: "Something went wrong!!")
                }
            }
        }
    }


}