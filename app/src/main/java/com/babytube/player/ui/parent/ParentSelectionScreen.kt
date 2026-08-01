package com.babytube.player.ui.parent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.babytube.player.data.model.VideoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentSelectionScreen(
    onBack: () -> Unit,
    viewModel: ParentViewModel = hiltViewModel()
) {
    val allVideos by viewModel.allVideos.collectAsState()
    val whitelistedIds by viewModel.whitelistedIds.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Modo Padre: Elige videos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (allVideos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Buscando videos...")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 250.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(padding)
            ) {
                items(allVideos, key = { it.id }) { video ->
                    val isSelected = whitelistedIds.contains(video.id.toString())
                    ParentVideoCard(
                        video = video,
                        isSelected = isSelected,
                        onToggle = { checked -> viewModel.toggleVideo(video.id, checked) }
                    )
                }
            }
        }
    }
}

@Composable
fun ParentVideoCard(
    video: VideoItem,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!isSelected) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggle(it) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = video.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${(video.size / 1024 / 1024)} MB",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
