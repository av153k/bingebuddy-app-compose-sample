package com.bingebuddy.app.network

import com.bingebuddy.app.data.network.model.DiscoverResponseModel
import com.bingebuddy.app.data.network.model.DiscoverMovieResultModel
import com.bingebuddy.app.data.network.model.DiscoverTvSeriesResultModel
import com.bingebuddy.app.data.network.model.MovieDetailsModel
import com.bingebuddy.app.data.network.model.SearchResultModel
import com.bingebuddy.app.data.network.model.TvSeriesDetailsModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    // For movies
    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: String): MovieDetailsModel


    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("sort_by") sortBy: String,
        @Query("with_release_type") releaseType: String? = null,
        @Query("release_date.lte") releaseDateLte: String? = null,
        @Query("release_date.gte") releaseDateGte: String? = null,
        @Query("vote_average.gte") minVoteAverage: Double? = null,
        @Query("vote_count.gte") minVoteCount: Int? = null
    ): DiscoverResponseModel<DiscoverMovieResultModel>

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = true
    ): DiscoverResponseModel<DiscoverMovieResultModel>


    // For Tv Series
    @GET("discover/tv")
    suspend fun discoverTV(
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("sort_by") sortBy: String,
        @Query("air_date.lte") airDateLte: String? = null,
        @Query("air_date.gte") airDateGte: String? = null,
        @Query("vote_average.gte") minVoteAverage: Double? = null,
        @Query("vote_count.gte") minVoteCount: Int? = null
    ): DiscoverResponseModel<DiscoverTvSeriesResultModel>

    @GET("trending/tv/{time_window}")
    suspend fun getTrendingTV(
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = true
    ): DiscoverResponseModel<DiscoverTvSeriesResultModel>

    @GET("tv/{tvSeriesId}")
    suspend fun getTvSeriesDetails(@Path("tvSeriesId") tvSeriesId: String): TvSeriesDetailsModel


    @GET("search/multi")
    suspend fun searchAll(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
        @Query("year") year: Int? = null,
        @Query("primary_release_year") primaryReleaseYear: Int? = null,
        @Query("first_air_date_year") firstAirDateYear: Int? = null
    ): DiscoverResponseModel<SearchResultModel>

}