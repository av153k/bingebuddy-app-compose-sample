package com.bingebuddy.app.data.network.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class ProductionCountry (
    @SerialName("iso_3166_1")
    val iso3166_1: String? = null,

    val name: String? = null
)
