package com.bingebuddy.app.data.repository

import com.bingebuddy.app.data.model.DiscoverResponseModel
import com.bingebuddy.app.model.DiscoverTvSeriesResultModel
import com.bingebuddy.app.model.TvSeriesDetailsModel
import com.bingebuddy.app.network.TmdbApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvSeriesRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) {
    suspend fun getAiringTodayTvSeries(): DiscoverResponseModel<DiscoverTvSeriesResultModel> = tmdbApiService.getAiringTodayTvSeries()

    suspend fun getPopularTvSeries(): DiscoverResponseModel<DiscoverTvSeriesResultModel> = tmdbApiService.getPopularTvSeries()

    suspend fun getTopRatedTvSeries(): DiscoverResponseModel<DiscoverTvSeriesResultModel> = tmdbApiService.getTopRatedTvSeries()

    suspend fun getOnTheAirTvSeries(): DiscoverResponseModel<DiscoverTvSeriesResultModel> = tmdbApiService.getOnTheAirTvSeries()

    suspend fun getTvSeriesDetails(tvSeriesId: String): TvSeriesDetailsModel = tmdbApiService.getTvSeriesDetails(tvSeriesId)
}