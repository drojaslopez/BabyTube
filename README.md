# BabyTube 🍼 📺
> *Un reproductor de video local ultraseguro diseñado específicamente para bebés y niños pequeños.*

BabyTube es una aplicación Android nativa construida para permitir que los más pequeños disfruten de videos guardados de forma local (en la memoria del dispositivo), proporcionando a los padres la tranquilidad de un sistema de reproducción blindado. Su característica estrella es el **Baby Touch Lock**, que previene pausas accidentales o interacciones indeseadas con el sistema operativo (llamadas, salidas a la pantalla de inicio, etc.).

---

## 🌟 Características Principales

*   **🛡️ Modo Padre con Control de Contenido (Nuevo):** Acceso protegido mediante un desafío matemático (Adult Gate) que permite seleccionar exactamente qué videos (`Whitelist`) se muestran en el perfil del bebé.
*   **🎞️ Selector Local Protegido:** Soporte nativo para MP4, MKV y WebM, filtrado para no mezclar contenido personal de los adultos.
*   **🔒 Bloqueo Táctil Extremo (Baby Touch Lock):** Al activarse, intercepta gestos de arrastre, pulsaciones y botones del sistema (Edge-to-Edge immersive).
*   **🔓 Desbloqueo Seguro:** Botón oculto que requiere mantener presionado ininterrumpidamente por 3 segundos para devolver el control al adulto, con retroalimentación visual de progreso.
*   **🔁 Reproducción en Bucle Infinito:** Diseñado para la paciencia infantil, cuenta con repetición ininterrumpida de los videos.
*   **⏱️ Temporizador de Apagado (Sleep Timer):** Configura pausas automáticas tras 15, 30, 45 o 60 minutos.
*   **📱 Paisaje Forzado:** Orientación 100% horizontal adaptada a los formatos de video.

---

## 🛠️ Stack Tecnológico (Modern Android Development)

El proyecto respeta los estándares modernos recomendados por Google:
- **Lenguaje:** Kotlin 1.9.20+
- **API Compatible:** Android 7.0 (API 24) a **Android 16 (API 36)**
- **UI & Diseño:** Jetpack Compose (Material 3)
- **Motor Multimedia:** AndroidX Media3 (ExoPlayer)
- **Inyección de Dependencias:** Hilt (`@HiltAndroidApp`)
- **Arquitectura & Estado:** MVVM con Kotlin Coroutines y `StateFlow`.
- **Navegación:** Jetpack Navigation Compose con Rutas Seguras (URI encoded).

---

## 🚀 Guía de Instalación (Desarrolladores)

1. **Clona el Repositorio** y ábrelo usando **Android Studio** (Koala o superior recomendado).
2. **Configuración de Gradle:** Asegúrate de tener seleccionado **JDK 17** en *Settings > Build, Execution, Deployment > Build Tools > Gradle*.
3. **Sincronización:** Android Studio descargará automáticamente el `gradle-wrapper.jar` y las dependencias de Compose y Hilt.
4. Presiona **Run (▶️)** o compila desde terminal con:
   ```bash
   ./gradlew assembleDebug
   ```

## 👶 Guía de Uso Rápido

1. Al abrir la app, deberás aceptar permisos de Almacenamiento (o Media en Android 13+).
2. Si la galería del bebé está vacía, aparecerá un mensaje de ayuda. Toca el icono del engranaje (⚙️) que se encuentra en la parte superior derecha.
3. Resuelve el desafío matemático mental.
4. Estarás en el **Cuarto de Control (Approve Videos)**. Marca los videos que desees autorizar.
5. Regresa atrás. ¡El bebé ahora solo verá tu selección!
6. Al poner un video, toca el icono del candado cerrado para blindar la pantalla.

---

## 📖 Especificaciones y Agentes (OpenSpec)
Revisa la carpeta `.openspec/` y `.agents/` para entender las métricas técnicas, la arquitectura de Intercepción Táctil (`LockOverlay`) y las pautas establecidas.
