package com.bingebuddy.app.data

import com.bingebuddy.app.model.GenreModel
import com.bingebuddy.app.model.MovieDetailsModel
import com.bingebuddy.app.model.ProductionCompany
import com.bingebuddy.app.model.ProductionCountry
import com.bingebuddy.app.model.SpokenLanguage

object PreviewData {
    val MOVIE_DETAIL = MovieDetailsModel(
        adult = false,
        backdropPath = "oOv2oUXcAaNXakRqUPxYq5lJURz.jpg",
        belongsToCollection = null,
        budget = 100000000L,
        genres = listOf(
            GenreModel(id = 28L, name = "Action"),
            GenreModel(id = 12L, name = "Adventure")
        ),
        homepage = "https://www.example.com",
        id = 12345L,
        imdbID = "tt1234567",
        originCountry = listOf("US"),
        originalLanguage = "en",
        originalTitle = "Test Movie",
        overview = "This is a test movie overview description.",
        popularity = 7.8,
        posterPath = "qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        productionCompanies = listOf(
            ProductionCompany(
                id = 1L,
                logoPath = "https://image.tmdb.org/t/p/w500/example_logo.png",
                name = "Example Studio",
                originCountry = "US"
            )
        ),
        productionCountries = listOf(
            ProductionCountry(
                iso3166_1 = "US",
                name = "United States of America"
            )
        ),
        releaseDate = "2025-01-01",
        revenue = 300000000L,
        runtime = 120L,
        spokenLanguages = listOf(
            SpokenLanguage(
                englishName = "English",
                iso639_1 = "en",
                name = "English"
            )
        ),
        status = "Released",
        tagline = "This is a test tagline.",
        title = "Test Movie",
        video = false,
        voteAverage = 8.5,
        voteCount = 1500L
    )
}