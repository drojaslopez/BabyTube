# BabyTube - Android Local Video Player for Babies

BabyTube es una aplicación Android nativa diseñada para permitir la reproducción de videos locales (almacenados en el dispositivo) para bebés y niños pequeños, evitando interrupciones accidentales mediante un sistema estricto de bloqueo táctil de pantalla (Child Touch Lock).

## Características

- **RF-01:** Selección de archivos locales (MP4, MKV, WebM) con permisos de almacenamiento
- **RF-02:** Reproducción forzada en orientación horizontal (Landscape) con modo inmersivo
- **RF-03:** Bloqueo táctil completo (Baby Touch Lock) para evitar interacciones accidentales
- **RF-04:** Mecanismo de desbloqueo seguro mediante pulsación prolongada (3 segundos)
- **RF-05:** Reproducción en bucle (Loop) para videos individuales
- **RF-06:** Temporizador de apagado (Sleep Timer) configurable (15, 30, 45, 60 minutos)

## Stack Tecnológico

- **Lenguaje:** Kotlin 1.9+
- **UI Framework:** Jetpack Compose
- **Media Engine:** AndroidX Media3 (ExoPlayer)
- **Inyección de Dependencias:** Hilt
- **Asincronía & Estado:** Kotlin Coroutines + StateFlow
- **System UI Control:** Accompanist System UI Controller

## Estructura del Proyecto

```
Babytube/
├── app/
│   ├── src/main/
│   │   ├── java/com/babytube/player/
│   │   │   ├── data/
│   │   │   │   ├── model/VideoItem.kt
│   │   │   │   └── repository/VideoRepository.kt
│   │   │   ├── di/
│   │   │   │   └── AppModule.kt
│   │   │   ├── ui/
│   │   │   │   ├── player/
│   │   │   │   │   ├── PlayerScreen.kt
│   │   │   │   │   ├── PlayerViewModel.kt
│   │   │   │   │   └── LockOverlay.kt
│   │   │   │   ├── videolist/
│   │   │   │   │   ├── VideoListScreen.kt
│   │   │   │   │   └── VideoListViewModel.kt
│   │   │   │   ├── permissions/
│   │   │   │   │   └── PermissionScreen.kt
│   │   │   │   ├── theme/
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   └── Type.kt
│   │   │   │   └── BabyTubeApp.kt
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   └── values/
│   │   │       ├── strings.xml
│   │   │       └── themes.xml
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── openspec/
│   ├── spec.md
│   └── architecture.md
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## Configuración

1. Configura tu SDK de Android en `local.properties`:
   ```
   sdk.dir=/path/to/your/android/sdk
   ```

2. Sincroniza el proyecto con Gradle

3. Compila y ejecuta en un dispositivo Android o emulador

## Permisos

La aplicación requiere los siguientes permisos:
- `READ_MEDIA_VIDEO` (Android 13+)
- `READ_EXTERNAL_STORAGE` (Android 12 y anteriores)

## Uso

1. Concede los permisos de almacenamiento cuando se soliciten
2. Selecciona un video de la lista
3. Usa los controles del reproductor:
   - **Icono de candado:** Bloquear/desbloquear la pantalla
   - **Icono de loop:** Activar/desactivar reproducción en bucle
   - **Icono de temporizador:** Configurar temporizador de apagado
   - **Flecha atrás:** Volver a la lista de videos

## Desbloqueo de Pantalla

Cuando la pantalla está bloqueada:
1. Mantén presionado el botón de desbloqueo (esquina superior derecha)
2. Espera 3 segundos hasta que el indicador circular se complete
3. La pantalla se desbloqueará y los controles reaparecerán

## Especificación OpenSpec

La especificación completa del proyecto se encuentra en la carpeta `openspec/`:
- `spec.md`: Requerimientos funcionales y criterios de aceptación
- `architecture.md`: Arquitectura técnica y stack tecnológico
