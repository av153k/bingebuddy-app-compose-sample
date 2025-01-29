package com.bingebuddy.app.di

import android.content.Context
import com.bingebuddy.app.AppStateManager
import com.bingebuddy.app.data.local.AppDatabase
import com.bingebuddy.app.data.local.model.WatchlistItem
import com.bingebuddy.app.data.local.repository.WatchlistItemDao
import com.bingebuddy.app.data.local.repository.WatchlistRepository
import com.bingebuddy.app.data.repository.MovieDetailsRepository
import com.bingebuddy.app.data.repository.MoviesRepository
import com.bingebuddy.app.data.repository.TvSeriesRepository
import com.bingebuddy.app.network.ApiKeyInterceptor
import com.bingebuddy.app.network.TmdbApiService
import com.bingebuddy.app.utils.PreferencesManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context) = PreferencesManager(context)

    @Provides
    fun provideAppStateManager(prefsManager: PreferencesManager) = AppStateManager(prefsManager)

    @Provides
    fun provideBaseUrl(): String = "https://v0-new-project-xthtyqwzpe7-abhishek-anands-projects-b1f4032d.vercel.app/api/tmdb/"

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
    @Singleton
    fun provideMovieRepository(apiService: TmdbApiService) =
        MoviesRepository(tmdbApiService = apiService)

    @Provides
    @Singleton
    fun provideTvSeriesRepository(apiService: TmdbApiService) =
        TvSeriesRepository(tmdbApiService = apiService)

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(apiService: TmdbApiService) =
        MovieDetailsRepository(tmdbApiService = apiService)

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideYourDao(appDatabase: AppDatabase): WatchlistItemDao {
        return appDatabase.watchlistItemDao()
    }

    @Provides
    fun provideWatchlistRepository(watchlistItemDao: WatchlistItemDao): WatchlistRepository {
        return WatchlistRepository(watchlistItemDao)
    }
}



