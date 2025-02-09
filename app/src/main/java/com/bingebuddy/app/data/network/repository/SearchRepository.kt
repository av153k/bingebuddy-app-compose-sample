package com.bingebuddy.app.data.network.repository

import android.content.Context
import com.bingebuddy.app.data.BaseRepository
import com.bingebuddy.app.network.TmdbApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService,
    context: Context,
): BaseRepository(context) {

    suspend fun searchAll(
        query: String,
        page: Int = 1,
        language: String? = null,
        region: String? = null,
        year: Int? = null,
        movieYear: Int? = null,
        tvYear: Int? = null,
        includeAdult: Boolean = false,
    ) = makeSafeApiCall {
        tmdbApiService.searchAll(
            query = query,
            page = page,
            includeAdult = includeAdult,
            language = language,
            region = region,
            year = year,
            primaryReleaseYear = movieYear,
            firstAirDateYear = tvYear
        )
    }
}