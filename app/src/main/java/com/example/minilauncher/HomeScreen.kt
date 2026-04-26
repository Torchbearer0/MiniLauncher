package com.example.minilauncher

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun MiniLauncherApp() {
    val context = LocalContext.current
    val apps = rememberInstalledApps(context)
    val scope = rememberCoroutineScope()
    var drawerVisible by remember { mutableStateOf(false) }
    var feedOffset by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        HomescreenGrid(apps = apps, onOpenApp = { launchApp(context, it.packageName) })

        RightSwipeFeed(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        feedOffset = (feedOffset + dragAmount).coerceIn(-320f, 0f)
                    }
                },
            offset = feedOffset
        )

        RetractableDock(
            modifier = Modifier.align(Alignment.BottomCenter),
            onOpenDrawer = { drawerVisible = true },
            onOpenApp = { launchApp(context, it.packageName) },
            apps = apps.take(5)
        )

        if (drawerVisible) {
            AppDrawer(apps = apps, onClose = { drawerVisible = false }, onOpenApp = {
                launchApp(context, it.packageName)
                drawerVisible = false
            })
        }
    }
}

@Composable
fun HomescreenGrid(apps: List<AppInfo>, onOpenApp: (AppInfo) -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(bottom = 88.dp)) {
        LazyVerticalGrid(columns = GridCells.Fixed(4), modifier = Modifier.fillMaxSize()) {
            items(apps) { app ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable { onOpenApp(app) }
                ) {
                    Image(
                        bitmap = app.icon.toBitmap().asImageBitmap(),
                        contentDescription = app.label,
                        modifier = Modifier.size(56.dp)
                    )
                    Text(app.label, maxLines = 1, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}
