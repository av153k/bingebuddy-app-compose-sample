package com.bingebuddy.app.data.network.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.bingebuddy.app.data.BaseRepository
import com.bingebuddy.app.data.network.model.DiscoverResponseModel
import com.bingebuddy.app.data.network.model.DiscoverMovieResultModel
import com.bingebuddy.app.network.TmdbApiService
import com.bingebuddy.app.utils.AsyncResult
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService,
    context: Context,
): BaseRepository(context) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNowPlayingMovies(
        page: Int = 1,
        includeAdult: Boolean = false
    ): AsyncResult<DiscoverResponseModel<DiscoverMovieResultModel>> {
        val today = LocalDate.now()
        val oneMonthAgo = today.minusMonths(1)

        return makeSafeApiCall {
            tmdbApiService.discoverMovies(
                page = page,
                includeAdult = includeAdult,
                sortBy = "primary_release_date.desc",
                releaseType = "2|3", // Theatrical and Digital
                releaseDateLte = today.toString(),
                releaseDateGte = oneMonthAgo.toString()
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUpcomingMovies(
        page: Int = 1,
        includeAdult: Boolean = false
    ): AsyncResult<DiscoverResponseModel<DiscoverMovieResultModel>> {
        val today = LocalDate.now()
        val sixMonthsAhead = today.plusMonths(6)

        return makeSafeApiCall {
            tmdbApiService.discoverMovies(
                page = page,
                includeAdult = includeAdult,
                sortBy = "primary_release_date.asc",
                releaseType = "2|3",
                releaseDateGte = today.toString(),
                releaseDateLte = sixMonthsAhead.toString()
            )
        }
    }

    suspend fun getPopularMovies(page: Int = 1, includeAdult: Boolean = false) =
        makeSafeApiCall {
            tmdbApiService.discoverMovies(
                page = page,
                includeAdult = false,
                sortBy = "popularity.desc",
                minVoteCount = 100
            )
        }

    suspend fun getTopRatedMovies(page: Int = 1, includeAdult: Boolean = false) =
        makeSafeApiCall {
            tmdbApiService.discoverMovies(
                page = page,
                includeAdult = includeAdult,
                sortBy = "vote_average.desc",
                minVoteCount = 200,
                minVoteAverage = 7.0
            )
        }

    suspend fun getTrendingMoviesDay(page: Int = 1, includeAdult: Boolean = false) =
        makeSafeApiCall {
            tmdbApiService.getTrendingMovies("day", page, includeAdult = includeAdult)
        }

    suspend fun getTrendingMoviesWeek(page: Int = 1, includeAdult: Boolean = false) =
        makeSafeApiCall {
            tmdbApiService.getTrendingMovies("week", page, includeAdult = includeAdult)
        }
}