package com.bingebuddy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bingebuddy.app.navigation.BingeBuddyNavGraph
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
        enableEdgeToEdge()
        setContent {
            BingeBuddyTheme {
                BingeBuddyApp()
            }
        }
    }
}

@Composable
fun BingeBuddyApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Timber.d("$innerPadding")

        BingeBuddyNavGraph(
            snackbarHostState = snackbarHostState
        )

    }
}

