package com.bingebuddy.app.di

import android.content.Context
import com.bingebuddy.app.AppStateManager
import com.bingebuddy.app.data.local.AppDatabase
import com.bingebuddy.app.data.local.model.WatchlistItem
import com.bingebuddy.app.data.local.repository.WatchlistItemDao
import com.bingebuddy.app.data.local.repository.WatchlistRepository
import com.bingebuddy.app.data.network.repository.AccountRepository
import com.bingebuddy.app.data.network.repository.MovieDetailsRepository
import com.bingebuddy.app.data.network.repository.MoviesRepository
import com.bingebuddy.app.data.network.repository.SearchRepository
import com.bingebuddy.app.data.network.repository.TvSeriesRepository
import com.bingebuddy.app.network.ApiKeyInterceptor
import com.bingebuddy.app.network.TmdbApiService
import com.bingebuddy.app.utils.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
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
    fun providePreferencesManager(@ApplicationContext context: Context) =
        PreferencesManager(context)

    @Provides
    fun provideAppStateManager(prefsManager: PreferencesManager) = AppStateManager(prefsManager)

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
    @Singleton
    fun provideMovieRepository(apiService: TmdbApiService, @ApplicationContext context: Context) =
        MoviesRepository(tmdbApiService = apiService, context)

    @Provides
    @Singleton
    fun provideTvSeriesRepository(
        apiService: TmdbApiService,
        @ApplicationContext context: Context
    ) =
        TvSeriesRepository(tmdbApiService = apiService, context)

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(
        apiService: TmdbApiService,
        @ApplicationContext context: Context
    ) =
        MovieDetailsRepository(tmdbApiService = apiService, context)

    @Provides
    @Singleton
    fun provideSearchRepository(apiService: TmdbApiService, @ApplicationContext context: Context) =
        SearchRepository(tmdbApiService = apiService, context)


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
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

    @Provides
    fun provideAccountRepository(@ApplicationContext context: Context) = AccountRepository(auth = FirebaseAuth.getInstance(), context = context)
}





