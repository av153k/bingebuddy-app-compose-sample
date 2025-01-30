package com.bingebuddy.app.data

import com.bingebuddy.app.data.local.model.WatchlistItem
import com.bingebuddy.app.data.network.model.DiscoverMovieResultModel
import com.bingebuddy.app.data.network.model.DiscoverTvSeriesResultModel
import com.bingebuddy.app.data.network.model.MediaType
import com.bingebuddy.app.data.network.model.MovieDetailsModel
import com.bingebuddy.app.data.network.model.SearchResultModel
import com.bingebuddy.app.data.network.model.TvSeriesDetailsModel

fun WatchlistItem.toMovie() = DiscoverMovieResultModel(
    id = tmdbId,
    title = name,
    releaseDate = releaseDate,
    adult = adult,
    posterPath = posterPath,
    voteCount = voteCount,
    voteAverage = voteAverage,
)

fun WatchlistItem.toTvSeries() = DiscoverTvSeriesResultModel(
    id = tmdbId,
    name = name,
    firstAirDate = releaseDate,
    adult = adult,
    posterPath = posterPath,
    voteCount = voteCount,
    voteAverage = voteAverage,
)

fun DiscoverMovieResultModel.toWatchlistItem() = WatchlistItem(
    tmdbId = id ?: 0,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate,
    name = title ?: "",
    adult = adult ?: false,
    voteAverage = voteAverage,
    voteCount = voteCount,
    type = "movie"
)

fun DiscoverTvSeriesResultModel.toWatchlistItem() = WatchlistItem(
    tmdbId = id ?: 0,
    posterPath = posterPath ?: "",
    releaseDate = firstAirDate,
    name = name ?: "",
    adult = adult ?: false,
    voteAverage = voteAverage,
    voteCount = voteCount,
    type = "tv-series"
)

fun MovieDetailsModel.toWatchlistItem() = WatchlistItem(
    tmdbId = id ?: 0,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate,
    name = title ?: "",
    adult = adult ?: false,
    voteAverage = voteAverage,
    voteCount = voteCount,
    type = "movie"
)

fun TvSeriesDetailsModel.toWatchlistItem() = WatchlistItem(
    tmdbId = id ?: 0,
    posterPath = posterPath ?: "",
    releaseDate = firstAirDate,
    name = name ?: "",
    adult = adult ?: false,
    voteAverage = voteAverage,
    voteCount = voteCount,
    type = "tv-series"
)

fun SearchResultModel.toWatchlistItem() = WatchlistItem(
    tmdbId = id ?: 0,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate,
    name = title ?: "",
    adult = adult ?: false,
    voteAverage = voteAverage,
    voteCount = voteCount,
    type = if (mediaType == MediaType.Movie) "movie" else "tv-series"
)