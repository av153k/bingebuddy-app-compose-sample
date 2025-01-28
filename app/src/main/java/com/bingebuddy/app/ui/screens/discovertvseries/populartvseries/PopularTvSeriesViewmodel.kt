package com.bingebuddy.app.ui.screens.discovertvseries.populartvseries

import com.bingebuddy.app.data.repository.TvSeriesRepository
import com.bingebuddy.app.model.DiscoverTvSeriesResultModel



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

const val TAG = "PopularTvSeriesViewmodel"

sealed interface PopularTvSeriesUiState {
    data class Success(val tvSeriesList: List<DiscoverTvSeriesResultModel>) : PopularTvSeriesUiState
    data object Error : PopularTvSeriesUiState
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
                uiState = try {
                    val discoverResult = tvSeriesRepository.getPopularTV(includeAdult = it)
                    PopularTvSeriesUiState.Success(
                        tvSeriesList = discoverResult.results ?: listOf()
                    )
                } catch (e: IOException) {
                    Timber.tag(TAG).e(e)
                    PopularTvSeriesUiState.Error
                } catch (e: HttpException) {
                    Timber.tag(TAG).e(e)
                    PopularTvSeriesUiState.Error
                }
            }
        }
    }


}