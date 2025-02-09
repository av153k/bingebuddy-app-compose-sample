package com.bingebuddy.app.data.network.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.bingebuddy.app.data.BaseRepository
import com.bingebuddy.app.data.network.model.DiscoverResponseModel
import com.bingebuddy.app.data.network.model.DiscoverTvSeriesResultModel
import com.bingebuddy.app.data.network.model.TvSeriesDetailsModel
import com.bingebuddy.app.network.TmdbApiService
import com.bingebuddy.app.utils.AsyncResult
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvSeriesRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService,
    context: Context,
) : BaseRepository(context) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAiringTodayTV(
        page: Int = 1,
        includeAdult: Boolean = false
    ): AsyncResult<DiscoverResponseModel<DiscoverTvSeriesResultModel>> {
        val today = LocalDate.now()

        return makeSafeApiCall {
            tmdbApiService.discoverTV(
                page = page,
                includeAdult = includeAdult,
                sortBy = "first_air_date.desc",
                airDateLte = today.toString(),
                airDateGte = today.toString()
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getOnTheAirTV(
        page: Int = 1,
        includeAdult: Boolean = false
    ): AsyncResult<DiscoverResponseModel<DiscoverTvSeriesResultModel>> {
        val today = LocalDate.now()
        val oneMonthAhead = today.plusMonths(1)

        return makeSafeApiCall {
            tmdbApiService.discoverTV(
                page = page,
                includeAdult = includeAdult,
                sortBy = "first_air_date.asc",
                airDateLte = oneMonthAhead.toString(),
                airDateGte = today.toString()
            )
        }
    }

    suspend fun getPopularTV(page: Int = 1, includeAdult: Boolean = false) =
        makeSafeApiCall {
            tmdbApiService.discoverTV(
                page = page,
                includeAdult = includeAdult,
                sortBy = "popularity.desc",
                minVoteCount = 100
            )
        }

    suspend fun getTopRatedTV(page: Int = 1, includeAdult: Boolean = false) =
        makeSafeApiCall {
            tmdbApiService.discoverTV(
                page = page,
                includeAdult = includeAdult,
                sortBy = "vote_average.desc",
                minVoteCount = 200,
                minVoteAverage = 7.0
            )
        }

    suspend fun getTrendingTVDay(page: Int = 1, includeAdult: Boolean = false) =
        makeSafeApiCall {
            tmdbApiService.getTrendingTV("day", page, includeAdult = includeAdult)

        }

    suspend fun getTrendingTVWeek(page: Int = 1, includeAdult: Boolean = false) =
        makeSafeApiCall {
            tmdbApiService.getTrendingTV("week", page, includeAdult = includeAdult)

        }

    suspend fun getTvSeriesDetails(tvSeriesId: String): AsyncResult<TvSeriesDetailsModel> =
        makeSafeApiCall {
            tmdbApiService.getTvSeriesDetails(tvSeriesId)
        }
}