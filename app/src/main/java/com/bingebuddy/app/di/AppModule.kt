package com.bingebuddy.app.di

import com.bingebuddy.app.data.repository.MoviesRepository
import com.bingebuddy.app.data.repository.TvSeriesRepository
import com.bingebuddy.app.network.ApiKeyInterceptor
import com.bingebuddy.app.network.TmdbApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = "https://dmm7vyhwyql4uk62.vercel.app/api/tmdb/"

    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .client(client)
            .build()
    }


    @Provides
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService =
        retrofit.create(TmdbApiService::class.java)

    @Provides
    fun provideMovieRepository(apiService: TmdbApiService) =
        MoviesRepository(tmdbApiService = apiService)

    @Provides
    fun provideTvSeriesRepository(apiService: TmdbApiService) =
        TvSeriesRepository(tmdbApiService = apiService)
}



