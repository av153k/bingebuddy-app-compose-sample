package com.bingebuddy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bingebuddy.app.network.TmdbApiService
import com.bingebuddy.app.ui.screens.home.HomeScreen

import com.bingebuddy.app.ui.theme.BingeBuddyTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

const val TAG = "BingeBuddyActivity"


@AndroidEntryPoint
class BingeBuddyActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("Api key - ${BuildConfig.API_KEY}")
        enableEdgeToEdge()
        setContent {
            BingeBuddyTheme {
                HomeScreen()
            }
        }
    }
}

