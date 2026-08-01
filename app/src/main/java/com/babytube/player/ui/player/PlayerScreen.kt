package com.babytube.player.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.babytube.player.data.model.VideoItem
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    video: VideoItem,
    onBack: () -> Unit,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val systemUiController = rememberSystemUiController()
    val uiState by viewModel.uiState.collectAsState()
    var showTimerDialog by remember { mutableStateOf(false) }

    // Set video on first composition
    LaunchedEffect(video) {
        viewModel.setVideo(video)
    }

    // Handle immersive mode
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    systemUiController.isSystemBarsVisible = false
                    systemUiController.isStatusBarVisible = false
                    systemUiController.isNavigationBarVisible = false
                }
                Lifecycle.Event.ON_PAUSE -> {
                    systemUiController.isSystemBarsVisible = true
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            systemUiController.isSystemBarsVisible = true
        }
    }

    // Handle sleep timer pause
    LaunchedEffect(uiState.shouldPause) {
        if (uiState.shouldPause) {
            delay(500)
            viewModel.clearPauseFlag()
            onBack()
        }
    }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setRepeatMode(
                if (uiState.isLoopEnabled) {
                    Player.REPEAT_MODE_ONE
                } else {
                    Player.REPEAT_MODE_OFF
                }
            )
        }
    }

    LaunchedEffect(uiState.mediaItem) {
        uiState.mediaItem?.let { mediaItem ->
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    LaunchedEffect(uiState.isLoopEnabled) {
        exoPlayer.repeatMode = if (uiState.isLoopEnabled) {
            Player.REPEAT_MODE_ONE
        } else {
            Player.REPEAT_MODE_OFF
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = !uiState.isLocked
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        LockOverlay(
            isLocked = uiState.isLocked,
            onUnlockProgress = { viewModel.updateUnlockProgress(it) },
            onUnlocked = { viewModel.toggleLock() }
        )

        if (!uiState.isLocked) {
            PlayerControls(
                modifier = Modifier.align(Alignment.BottomCenter),
                isLocked = uiState.isLocked,
                isLoopEnabled = uiState.isLoopEnabled,
                sleepTimerMinutes = uiState.sleepTimerMinutes,
                onLockToggle = { viewModel.toggleLock() },
                onLoopToggle = { viewModel.toggleLoop() },
                onSleepTimerClick = { showTimerDialog = true },
                onBack = onBack
            )
        }
    }

    if (showTimerDialog) {
        SleepTimerDialog(
            selectedMinutes = uiState.sleepTimerMinutes,
            onDismiss = { showTimerDialog = false },
            onConfirm = { minutes ->
                viewModel.setSleepTimer(minutes)
                showTimerDialog = false
            }
        )
    }
}

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isLocked: Boolean,
    isLoopEnabled: Boolean,
    sleepTimerMinutes: Int?,
    onLockToggle: () -> Unit,
    onLoopToggle: () -> Unit,
    onSleepTimerClick: () -> Unit,
    onBack: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            IconButton(onClick = onLockToggle) {
                Icon(
                    if (isLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                    contentDescription = if (isLocked) "Unlock" else "Lock"
                )
            }

            IconButton(onClick = onLoopToggle) {
                Icon(
                    Icons.Default.Loop,
                    contentDescription = "Loop",
                    tint = if (isLoopEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(onClick = onSleepTimerClick) {
                Icon(
                    Icons.Default.Timer,
                    contentDescription = "Sleep Timer",
                    tint = if (sleepTimerMinutes != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}

@Composable
fun SleepTimerDialog(
    selectedMinutes: Int?,
    onDismiss: () -> Unit,
    onConfirm: (Int?) -> Unit
) {
    val options = listOf(15, 30, 45, 60)
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Sleep Timer") },
        text = {
            Column {
                options.forEach { minutes ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedMinutes == minutes,
                            onClick = { onConfirm(minutes) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("$minutes minutes")
                    }
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedMinutes == null,
                        onClick = { onConfirm(null) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Off")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
