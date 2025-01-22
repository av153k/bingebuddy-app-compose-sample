package com.bingebuddy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bingebuddy.app.features.home.HomeScreen

import com.bingebuddy.app.ui.theme.BingeBuddyTheme

class BingeBuddyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BingeBuddyTheme {
                HomeScreen()
            }
        }
    }
}

