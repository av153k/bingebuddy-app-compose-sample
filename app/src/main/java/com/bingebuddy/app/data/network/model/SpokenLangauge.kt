package com.bingebuddy.app.data.network.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SpokenLanguage (
    @SerialName("english_name")
    val englishName: String? = null,

    @SerialName("iso_639_1")
    val iso639_1: String? = null,

    val name: String? = null
)
