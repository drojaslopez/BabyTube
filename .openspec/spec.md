# Open Spec: BabyTube (Android Local Video Player for Babies)

## 1. Contexto y Visión General
BabyTube es una aplicación Android nativa diseñada para permitir la reproducción de videos locales (almacenados en el dispositivo) para bebés y niños pequeños, evitando interrupciones accidentales mediante un sistema estricto de bloqueo táctil de pantalla (Child Touch Lock) inspirado en plataformas como Netflix.

## 2. Requerimientos Funcionales y Criterios de Aceptación

### RF-01: Selección de Archivos Locales
- **Descripción:** La app debe permitir al usuario explorar y seleccionar archivos de video (MP4, MKV, WebM) almacenados localmente.
- **Criterios de Aceptación:**
  - *Dado que* el usuario otorga los permisos de lectura de medios (`READ_MEDIA_VIDEO` o `READ_EXTERNAL_STORAGE`).
  - *Cuando* abra la pantalla principal.
  - *Entonces* verá una lista o cuadrícula con los videos disponibles con su miniatura, título y duración.

### RF-02: Reproducción e Interfaz Horizontal (Landscape)
- **Descripción:** La pantalla del reproductor debe forzarse siempre en orientación horizontal.
- **Criterios de Aceptación:**
  - *Dado que* el usuario selecciona un video para reproducir.
  - *Cuando* se abra la pantalla del reproductor.
  - *Entonces* la orientación de la pantalla cambiará inmediatamente a Landscape y ocultará las barras del sistema (Immersive Sticky Mode).

### RF-03: Bloqueo Táctil (Baby Touch Lock)
- **Descripción:** Bloqueo completo de eventos táctiles en la pantalla para evitar pausar, cambiar de video o interactuar con el sistema Android.
- **Criterios de Aceptación:**
  - *Dado que* el video está en reproducción.
  - *Cuando* el usuario toque el botón "Bloquear Pantalla".
  - *Entonces* los controles de reproducción desaparecerán y la pantalla dejará de responder a cualquier toque estándar.
  - *Cuando* se toque la pantalla estando bloqueada, únicamente se mostrará un indicador sutil en la zona de desbloqueo (Top-Right / Centro-Derecha superior).

### RF-04: Mecanismo de Desbloqueo Seguro
- **Descripción:** Desbloqueo mediante pulsación prolongada (Long Press) en una zona táctil específica.
- **Criterios de Aceptación:**
  - *Dado que* la pantalla está en estado bloqueado.
  - *Cuando* el usuario mantenga presionada la zona activa (Top-Right) por un periodo de 3 segundos.
  - *Entonces* un indicador visual circular completará su llenado y la pantalla se desbloqueará, reapareciendo los controles.
  - *Si* el usuario suelta antes de cumplir los 3 segundos, la acción se cancela y la pantalla permanece bloqueada.

### RF-05: Reproducción en Bucle (Loop)
- **Descripción:** Opción para que el video actual o la lista seleccionada se repita automáticamente al finalizar.
- **Criterios de Aceptación:**
  - *Dado que* la opción de Bucle está activada.
  - *Cuando* el video alcance el segundo final.
  - *Entonces* recomenzará la reproducción desde el segundo 0 sin pausar ni salir al menú.

### RF-06: Temporizador de Apagado (Sleep Timer)
- **Descripción:** Detener la reproducción automáticamente tras un tiempo configurado.
- **Criterios de Aceptación:**
  - *Dado que* el usuario programa el temporizador (ej. 15, 30, 45 minutos).
  - *Cuando* el tiempo transcurra por completo.
  - *Entonces* la reproducción se pausará y la pantalla bajará el brillo al mínimo o se apagará.

### RF-07: Control Parental (Modo Adulto / Whitelist)
- **Descripción:** Proteger la biblioteca de videos para que el bebé solo tenga acceso a un catálogo pre-autorizado por el guardián.
- **Criterios de Aceptación:**
  - *Dado que* un usuario adulto quiere agregar videos.
  - *Cuando* toque el botón de Configuración en la lista principal de videos.
  - *Entonces* saltará un desafío matemático aleatorio ("Adult Gate").
  - *Si* el adulto acierta, entrará a la vista de selección masiva donde podrá activar casillas de verificación (Checkboxes) conservando su registro al reiniciar la app mediante almacenamiento persistente persistencia (`SharedPreferences`).
  - *Entonces* la pantalla del bebé se actualizará en tiempo real para mostrar y reproducir única y exclusivamente los elementos aprobados.
