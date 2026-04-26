package com.example.minilauncher

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class AppInfo(val label: String, val packageName: String, val icon: Drawable)

@Composable
fun rememberInstalledApps(context: Context): List<AppInfo> {
    val apps = remember { mutableStateListOf<AppInfo>() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val pm = context.packageManager
            val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
            val list = pm.queryIntentActivities(intent, 0)
                .map {
                    AppInfo(
                        label = it.loadLabel(pm).toString(),
                        packageName = it.activityInfo.packageName,
                        icon = it.loadIcon(pm)
                    )
                }.sortedBy { it.label.lowercase() }
            apps.clear()
            apps.addAll(list)
        }
    }
    return apps
}
