package com.bingebuddy.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage (
    @SerialName("english_name")
    val englishName: String? = null,

    @SerialName("iso_639_1")
    val iso639_1: String? = null,

    val name: String? = null
)
