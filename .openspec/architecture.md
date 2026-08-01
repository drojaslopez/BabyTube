# Arquitectura Técnica: BabyTube

## 1. Stack Tecnológico
- **Lenguaje:** Kotlin (1.9+)
- **UI Framework:** Jetpack Compose
- **Media Engine:** AndroidX Media3 (ExoPlayer)
- **Inyección de Dependencias:** Hilt / Koin
- **Asincronía & Estado:** Kotlin Coroutines + StateFlow / SharedFlow
- **System UI Control:** Accompanist System UI Controller / WindowInsetsControllerCompat

## 2. Estrategia Técnica para Touch Locking (Baby Lock)

### Interceptación de Eventos Táctiles
En Jetpack Compose, el bloqueo se logra superponiendo una capa `Box` de nivel superior sobre el `PlayerView` de ExoPlayer:

```kotlin
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
                onUnlocked = onUnlocked
            )
        }
    }
}
