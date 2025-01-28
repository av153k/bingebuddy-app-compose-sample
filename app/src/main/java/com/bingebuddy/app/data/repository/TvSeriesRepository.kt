package com.bingebuddy.app.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.bingebuddy.app.data.model.DiscoverResponseModel
import com.bingebuddy.app.model.DiscoverTvSeriesResultModel
import com.bingebuddy.app.model.TvSeriesDetailsModel
import com.bingebuddy.app.network.TmdbApiService
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvSeriesRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAiringTodayTV(page: Int = 1, includeAdult: Boolean = false ): DiscoverResponseModel<DiscoverTvSeriesResultModel> {
        val today = LocalDate.now()

        return tmdbApiService.discoverTV(
            page = page,
            includeAdult = includeAdult,
            sortBy = "first_air_date.desc",
            airDateLte = today.toString(),
            airDateGte = today.toString()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getOnTheAirTV(page: Int = 1, includeAdult: Boolean = false ): DiscoverResponseModel<DiscoverTvSeriesResultModel> {
        val today = LocalDate.now()
        val oneMonthAhead = today.plusMonths(1)

        return tmdbApiService.discoverTV(
            page = page,
            includeAdult = includeAdult,
            sortBy = "first_air_date.asc",
            airDateLte = oneMonthAhead.toString(),
            airDateGte = today.toString()
        )
    }

    suspend fun getPopularTV(page: Int = 1, includeAdult: Boolean = false ) =
        tmdbApiService.discoverTV(
            page = page,
            includeAdult = includeAdult,
            sortBy = "popularity.desc",
            minVoteCount = 100
        )

    suspend fun getTopRatedTV(page: Int = 1, includeAdult: Boolean = false ) =
        tmdbApiService.discoverTV(
            page = page,
            includeAdult = includeAdult,
            sortBy = "vote_average.desc",
            minVoteCount = 200,
            minVoteAverage = 7.0
        )

    suspend fun getTrendingTVDay(page: Int = 1, includeAdult: Boolean = false ) =
        tmdbApiService.getTrendingTV("day", page, includeAdult = includeAdult)

    suspend fun getTrendingTVWeek(page: Int = 1, includeAdult: Boolean = false ) =
        tmdbApiService.getTrendingTV("week", page, includeAdult = includeAdult)

    suspend fun getTvSeriesDetails(tvSeriesId: String,): TvSeriesDetailsModel = tmdbApiService.getTvSeriesDetails(tvSeriesId,)
}