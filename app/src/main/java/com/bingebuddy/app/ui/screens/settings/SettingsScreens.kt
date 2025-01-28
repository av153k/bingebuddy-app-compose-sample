@file:OptIn(ExperimentalMaterial3Api::class)

package com.bingebuddy.app.ui.screens.settings

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.sharp.ArrowBackIosNew
import androidx.compose.material.icons.sharp.Computer
import androidx.compose.material.icons.sharp.Devices
import androidx.compose.material.icons.sharp.Explicit
import androidx.compose.material.icons.sharp.Nightlight
import androidx.compose.material.icons.sharp.WbSunny
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bingebuddy.app.AppViewmodel
import com.bingebuddy.app.ui.screens.home.getAppVersion
import com.bingebuddy.app.ui.theme.ThemeMode

@Composable
fun SettingsScreen(
    navigateUp: () -> Unit,
    viewmodel: AppViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val appVersion = remember { getAppVersion(context) }

    val themeMode by viewmodel.themeMode.collectAsState()
    val allowExplicitContent by viewmodel.allowExplicitContents.collectAsState()
    Scaffold(
        topBar =
        {
            CenterAlignedTopAppBar(
                title = {
                    Text("Settings", style = MaterialTheme.typography.headlineSmall)
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateUp
                    ) {
                        Icon(Icons.Sharp.ArrowBackIosNew, contentDescription = "back-from-settings")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                item {
                    ThemeSelectionTile(
                        themeMode = themeMode,
                        onThemeChange = {
                            viewmodel.setThemeMode(it)
                        }
                    )
                    SettingsSwitchTile(
                        icon = Icons.Sharp.Explicit,
                        label = "Allow explicit content",
                        description = "Adult movies and tv shows will be included everywhere",
                        checked = allowExplicitContent,
                        onClick = {
                            viewmodel.setExplicitContentSettings(it)
                        },
                    )

                }
            }
        }
    }
}


@Composable
fun SettingsSwitchTile(
    icon: ImageVector,
    label: String,
    description: String,
    checked: Boolean = false,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), verticalAlignment = Alignment.Top
    ) {
        Icon(
            icon, contentDescription = label,
            modifier = Modifier.size(30.dp)
        )
        Spacer(Modifier.width(5.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.width(5.dp))
            Text(description, style = MaterialTheme.typography.bodySmall, maxLines = 5)
        }
        Spacer(Modifier.width(10.dp))
        Switch(
            checked = checked,
            onCheckedChange = onClick,
        )
    }
}

@Composable
fun ThemeSelectionTile(
    themeMode: ThemeMode,
    onThemeChange: (mode: ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if (themeMode == ThemeMode.SYSTEM) Icons.Sharp.Computer else if (themeMode == ThemeMode.DARK) Icons.Sharp.Nightlight else Icons.Sharp.WbSunny,
            contentDescription = "theme-selection",
            modifier = Modifier.size(30.dp)
        )
        Spacer(Modifier.width(5.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("Current theme mode", style = MaterialTheme.typography.bodyMedium)

        }
        Spacer(Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = { expanded = !expanded }) {
                Text(themeMode.name)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                ThemeMode.entries.forEachIndexed { _, option ->
                    DropdownMenuItem(
                        text = { Text(option.name.capitalize(Locale("en"))) },
                        onClick = {

                            expanded = false
                            onThemeChange(option);
                        }
                    )
                }
            }
        }
    }

}

