package com.bingebuddy.app.ui.screens.home

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bingebuddy.app.navigation.BingeBuddyScreens
import kotlinx.coroutines.launch

@Composable
fun BingeBuddyDrawer(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    navigateTo: (route: String) -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val appVersion = remember { getAppVersion(context) }

    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.width(300.dp),
            ) {
                Scaffold(

                ) { innerPadding ->
                    Column(modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Bingebuddy",
                                style = MaterialTheme.typography.displaySmall,
                            )
                        }
                        DrawerTile(icon = Icons.Sharp.Person, label = "Profile") {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Feature in progress",
                                    actionLabel = "Dismiss"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        DrawerTile(icon = Icons.Sharp.Search, label = "Search") {
                            navigateTo(BingeBuddyScreens.Search.route)
                        }
                        Spacer(modifier = Modifier.height(500.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("v${appVersion}", style = MaterialTheme.typography.bodyLarge)
                        }


                    }
                }
            }
        }
    ) {
        content()
    }
}

@Composable
private fun DrawerTile(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 15.dp)
            .clickable {
                onClick()
            },
    ) {
        Icon(
            icon,
            contentDescription = label,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, style = MaterialTheme.typography.headlineSmall)
    }
}

fun getAppVersion(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "Unknown"
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}
