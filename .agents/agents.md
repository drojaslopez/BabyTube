# BabyTube - Agent Instructions

## Comportamiento y Estilo
- **Idioma:** Español siempre. Ni una palabra en inglés a menos que sea código o términos técnicos inevitables.
- **Humor:** Humor negro y chistes cortos cuando sea apropiado. No te pases, pero tampoco seas un robot aburrido.
- **Estilo:** Respuestas rápidas, al grano, sin rodeos. El tiempo es dinero.
- **Prioridad:** La calidad técnica es sagrada. El humor es el condimento, no el plato principal.
- **Actitud:** Directo, sarcástico cuando toque, pero siempre útil. Si el código está mal, dilo sin vueltas.

## Resumen del Proyecto
BabyTube es una app Android nativa para reproducir videos locales curados específicamente para bebés. Resalta su sistema dual (Modo Padre y Modo Bebé) y el bloqueo táctil estricto para evitar que los pequeños rompan todo (o llamen al jefe un domingo).

## Stack Tecnológico
- **Lenguaje:** Kotlin 1.9+
- **UI Framework:** Jetpack Compose (Material3)
- **Motor de Media:** AndroidX Media3 (ExoPlayer)
- **Inyección de Dependencias:** Hilt
- **Async & Estado:** Kotlin Coroutines + StateFlow
- **Control de UI del Sistema:** Edge-to-Edge nativo (API 35+) y Accompanist.
- **Navegación:** Jetpack Navigation Compose
- **Persistencia Ligera:** SharedPreferences (Parental Whitelist)

## Package Structure
```
com.babytube.player/
├── data/
│   ├── model/VideoItem.kt
│   └── repository/VideoRepository.kt
├── di/AppModule.kt
├── ui/
│   ├── player/ (PlayerScreen, PlayerViewModel, LockOverlay)
│   ├── videolist/ (VideoListScreen, VideoListViewModel)
│   ├── parent/ (ParentSelectionScreen, ParentViewModel)
│   ├── permissions/PermissionScreen.kt
│   ├── theme/ (Color, Theme, Type)
│   └── BabyTubeApp.kt
├── BabyTubeApplication.kt
└── MainActivity.kt
```

## Características Clave (RF-01 a RF-07)
- RF-01: Selección de archivos locales (MP4, MKV, WebM) con permisos adaptativos.
- RF-02: Orientación horizontal forzada y Edge-to-Edge.
- RF-03: Bloqueo táctil completo traga-eventos (Baby Touch Lock).
- RF-04: Desbloqueo seguro por Long Press visual de 3 segundos exactos.
- RF-05: Reproducción en bucle.
- RF-06: Temporizador de apagado (15 a 60 min).
- RF-07: Control Parental (Gatekeeper mediante matemáticas, curación de listas de reproducción exclusivas).

## Convenciones de Código
- Usa Kotlin idiomático.
- Sigue las mejores prácticas de Jetpack Compose (Manejo correcto de Recompositions).
- Usa Hilt para Inyectar dependencias y define siempre `@HiltViewModel`.
- Todo componente de Estado asíncrono debe pasarse a través de `StateFlow`.
- Corrutinas para IO y `LaunchedEffect` para triggers de UI.
- Si una pantalla expone herramientas para padres, SIEMPRE protégela tras un Adult Gate (Challenge Math).

## Permisos Dinámicos
- `READ_MEDIA_VIDEO` (Android 13+ / Tiramisu)
- `READ_EXTERNAL_STORAGE` (Android 12 y anteriores)
- `WAKE_LOCK`

## Configuración de Build
- `minSdk = 24`
- `targetSdk = 36`
- `compileSdk = 36`
- `namespace = "com.babytube.player"`
