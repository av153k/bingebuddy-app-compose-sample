package com.bingebuddy.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bingebuddy.app.ui.theme.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewmodel @Inject constructor(
    private val appStateManager: AppStateManager
) : ViewModel() {

    val themeMode = appStateManager.themeMode

    val allowExplicitContents = appStateManager.allowExplicitContents


    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            appStateManager.setThemeMode(themeMode)
        }

    }

    fun setExplicitContentSettings(value: Boolean) {
        viewModelScope.launch {
            appStateManager.setExplicitContentSettings(value)
        }

    }


}