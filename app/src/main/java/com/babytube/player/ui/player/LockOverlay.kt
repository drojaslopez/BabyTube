package com.babytube.player.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min

@Composable
fun LockOverlay(
    isLocked: Boolean,
    onUnlockProgress: (Float) -> Unit,
    onUnlocked: () -> Unit
) {
    if (isLocked) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            event.changes.forEach { it.consume() }
                        }
                    }
                }
        ) {
            UnlockZoneButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 64.dp),
                onUnlockProgress = onUnlockProgress,
                onUnlocked = onUnlocked
            )
        }
    }
}

@Composable
fun UnlockZoneButton(
    modifier: Modifier = Modifier,
    onUnlockProgress: (Float) -> Unit,
    onUnlocked: () -> Unit
) {
    var progress by remember { mutableFloatStateOf(0f) }
    var isPressed by remember { androidx.compose.runtime.mutableStateOf(false) }
    val unlockDuration = 3000f // 3 seconds
    
    LaunchedEffect(isPressed) {
        if (isPressed) {
            while (progress < 1f) {
                delay(16)
                progress = min(progress + (16f / unlockDuration), 1f)
                onUnlockProgress(progress)
            }
            if (progress >= 1f) {
                onUnlocked()
                progress = 0f
                isPressed = false
            }
        } else {
            progress = 0f
            onUnlockProgress(0f)
        }
    }

    Box(
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.5f))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Unlock",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
        
        // Progress indicator
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    Color.White.copy(alpha = progress * 0.3f)
                )
        )
    }
}
