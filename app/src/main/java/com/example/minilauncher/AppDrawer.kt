package com.example.minilauncher

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(apps: List<AppInfo>, onClose: () -> Unit = {}, onOpenApp: (AppInfo) -> Unit) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(androidx.compose.ui.graphics.Color(0xFFFAFAFA))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("All apps")
                Spacer(modifier = Modifier.weight(1f))
                Text("Close", modifier = Modifier.clickable { onClose() })
            }
            LazyVerticalGrid(columns = GridCells.Fixed(4), modifier = Modifier.fillMaxSize()) {
                items(apps) { app ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable { onOpenApp(app) }
                    ) {
                        Image(bitmap = app.icon.toBitmap().asImageBitmap(), contentDescription = app.label, modifier = Modifier.size(56.dp))
                        Text(app.label, modifier = Modifier.padding(top = 6.dp))
                    }
                }
            }
        }
    }
}
