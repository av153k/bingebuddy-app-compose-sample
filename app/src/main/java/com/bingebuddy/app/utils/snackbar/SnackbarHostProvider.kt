package com.bingebuddy.app.utils.snackbar

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHostState =  compositionLocalOf<SnackbarHostState> {
    throw Error("No snackbar host provided")
}

@Composable
fun ProvideSnackbarHost(content: @Composable () -> Unit) {
    val snackbarHostState = SnackbarHostState()

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        SnackbarHost(snackbarHostState)
        content()
    }
}