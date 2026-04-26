package com.example.minilauncher

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RetractableDock(
    modifier: Modifier = Modifier,
    onOpenDrawer: () -> Unit,
    onOpenApp: (AppInfo) -> Unit,
    apps: List<AppInfo>
) {
    var expanded by remember { mutableStateOf(false) }
    val height = if (expanded) 220.dp else 72.dp

    Surface(modifier = modifier
        .fillMaxWidth()
        .height(height)
        .clickable { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onOpenDrawer) {
                Text("≡")
            }
            Row {
                apps.forEach { app ->
                    androidx.compose.foundation.Image(
                        bitmap = app.icon.toBitmap().asImageBitmap(),
                        contentDescription = app.label,
                        modifier = Modifier
                            .size(56.dp)
                            .padding(horizontal = 6.dp)
                            .clickable { onOpenApp(app) }
                    )
                }
            }
            IconButton(onClick = { /* search placeholder */ }) {
                Text("🔍")
            }
        }
    }
}
