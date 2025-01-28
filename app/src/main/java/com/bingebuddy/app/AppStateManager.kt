package com.bingebuddy.app

import androidx.lifecycle.viewModelScope
import com.bingebuddy.app.ui.theme.ThemeMode
import com.bingebuddy.app.utils.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppStateManager @Inject constructor(
    private val prefsManager: PreferencesManager
) {

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode = _themeMode.asStateFlow()

    private val _allowExplicitContents = MutableStateFlow(false)
    val allowExplicitContents = _allowExplicitContents.asStateFlow()

    init {
        observeSettings()
    }

    private fun observeSettings() {
        CoroutineScope(Dispatchers.IO).launch {
            prefsManager.get(PreferencesManager.THEME_MODE, ThemeMode.SYSTEM.name)
                .collect { theme ->
                    _themeMode.value = ThemeMode.valueOf(theme)
                }
        }
        CoroutineScope(Dispatchers.IO).launch {
            prefsManager.get<Boolean>(PreferencesManager.EXPLICIT_CONTENT_ALLOWED, false)
                .collect {
                    _allowExplicitContents.value = it
                }
        }
    }

   suspend fun setThemeMode(themeMode: ThemeMode) {
            prefsManager.put(PreferencesManager.THEME_MODE, themeMode.name)

    }

    suspend fun setExplicitContentSettings(value: Boolean) {
            prefsManager.put(PreferencesManager.EXPLICIT_CONTENT_ALLOWED, value)

    }

}