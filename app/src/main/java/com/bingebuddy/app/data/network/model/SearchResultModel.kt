package com.bingebuddy.app.data.network.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*


@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SearchResultModel (
    val id: Long? = null,
    val name: String? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    @SerialName("media_type")
    val mediaType: MediaType? = null,

    val adult: Boolean? = null,
    val popularity: Double? = null,
    val gender: Long? = null,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("known_for")
    val knownFor: List<SearchResultModel>? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    val title: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    val overview: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    val video: Boolean? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Long? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("origin_country")
    val originCountry: List<String>? = null
)


@Serializable
enum class MediaType(val value: String) {
    @SerialName("movie") Movie("movie"),
    @SerialName("person") Person("person"),
    @SerialName("tv") Tv("tv");
}
