# ViewModel Fácil

Aplicación Android escrita en **Kotlin** que explica, de forma sencilla, el concepto de **ViewModel**.

Trabajo académico. Enunciado:

> «Crear una aplicación móvil en Kotlin, donde se explique el concepto de ViewModel. Debe ser una aplicación sencilla. Donde solo haya un viewmodel y por mucho dos view.»

| Requisito | Cómo se cumple |
|---|---|
| App en Kotlin | Todo el código de la app está en Kotlin (Jetpack Compose). |
| Un solo ViewModel | `ContadorViewModel` es la única clase que hereda de `ViewModel`. |
| Máximo dos vistas | Dos pantallas: `PantallaExplicacion` y `PantallaContador`. |
| Sencilla | Sin base de datos, sin internet, sin registro, sin anuncios. |

---

## ¿Qué es un ViewModel?

Un **ViewModel** es una clase de Android que guarda **los datos que muestra una pantalla** y **la lógica para cambiarlos**.

- **Separa responsabilidades:** la interfaz (lo que ves) solo dibuja y avisa cuando pulsas un botón. El ViewModel guarda los datos y decide cómo cambian.
- **Conserva el estado ante cambios de configuración:** al girar el teléfono, Android destruye la pantalla (la *Activity*) y la vuelve a crear. El ViewModel **no** se destruye en ese momento, así que los datos siguen ahí.
- **Aclaración importante:** el ViewModel guarda los datos **en memoria**. Si Android cierra la app por completo (por ejemplo, para liberar memoria), los datos se pierden. Para este ejercicio no se usa base de datos ni almacenamiento permanente.

---

## Las dos pantallas

### Pantalla 1 — «¿Qué es un ViewModel?»
- Título «¿Qué es un ViewModel?».
- Explicación breve en español: qué es, cómo separa los datos de la interfaz y cómo conserva el estado al girar la pantalla.
- Botón **«Probar ejemplo»**: abre la pantalla 2.

### Pantalla 2 — «Ejemplo práctico»
- Un contador que empieza en **0**.
- Botón **«+1»**: suma 1 al contador.
- Botón **«Reiniciar»**: vuelve el contador a 0.
- Botón **«Volver»**: regresa a la pantalla 1 (el botón *Atrás* del teléfono hace lo mismo).
- Texto que explica que el número lo guarda el `ContadorViewModel`, no la pantalla.

Si vuelves a la pantalla 1 y entras otra vez, el número **se conserva**, porque el ViewModel sigue vivo. Si giras el teléfono, el número también se conserva.

---

## ¿Por qué hay un solo ViewModel?

1. El profesor pidió **solo un ViewModel**.
2. Solo la pantalla 2 tiene datos que cambian (el contador). La pantalla 1 solo muestra texto, así que **no necesita** ViewModel.
3. La pantalla que se está mostrando (1 o 2) se guarda con una variable sencilla de Compose (`rememberSaveable`) en `MainActivity`, sin usar otro ViewModel ni una librería de navegación.

**Interpretación de «por mucho dos view»:** se entendió como **dos pantallas** como máximo. Por eso la app tiene exactamente dos funciones de pantalla y no crea componentes visuales propios adicionales.

---

## Tecnologías utilizadas

| Herramienta | Versión |
|---|---|
| Kotlin | 2.2.10 |
| Jetpack Compose (BOM) | 2026.02.01 con Material 3 |
| AndroidX Lifecycle (ViewModel) | 2.11.0 |
| AndroidX Activity Compose | 1.13.0 |
| Android Gradle Plugin | 9.4.1 |
| Gradle | 9.6.0 (incluido con `gradlew`) |
| SDK de compilación / objetivo | Android 37 |
| Versión mínima de Android | 8.0 (API 26) |
| Pruebas | JUnit 4 y Compose UI Test |

El estado del contador se expone con **`StateFlow`** (de Kotlin), y la pantalla lo observa con `collectAsState()`.

---

## Requisitos para abrir y ejecutar

- **Android Studio** reciente (se probó con Android Studio 2026.2). Incluye el JDK necesario.
- **Android SDK** con la plataforma Android 37 (Android Studio la descarga si falta).
- Un **emulador** o un **teléfono Android** 8.0 o superior.
- Conexión a internet la primera vez (Gradle descarga las librerías).

## Cómo abrirlo en Android Studio

1. Descarga el proyecto: botón verde **Code → Download ZIP** en GitHub y descomprímelo, o usa
   `git clone https://github.com/lxcruz404/ViewModelFacil.git`.
2. En Android Studio: **File → Open…** y elige la carpeta `ViewModelFacil`.
3. Espera a que termine la sincronización de Gradle (barra inferior).
4. Elige un emulador o teléfono arriba y pulsa **Run ▶**.

## Cómo compilarlo desde la terminal

En Windows (dentro de la carpeta del proyecto):

```bash
gradlew.bat assembleDebug
```

En macOS / Linux:

```bash
./gradlew assembleDebug
```

Pruebas unitarias (en el PC, sin emulador):

```bash
gradlew.bat testDebugUnitTest
```

Pruebas de interfaz (con un emulador abierto o un teléfono conectado):

```bash
gradlew.bat connectedDebugAndroidTest
```

## ¿Dónde queda el APK?

Después de compilar, el APK de depuración se genera en:

```
app/build/outputs/apk/debug/app-debug.apk
```

(La carpeta `build/` no se sube a GitHub; se crea al compilar.)

---

## Estructura de archivos importantes

```
ViewModelFacil/
├── app/
│   ├── build.gradle.kts                  ← Configuración y librerías de la app
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml       ← Nombre de la app y Activity inicial
│       │   ├── res/values/               ← Nombre de la app y tema de la ventana
│       │   └── java/com/luiscruz/viewmodelfacil/
│       │       ├── ContadorViewModel.kt  ← EL ÚNICO VIEWMODEL (guarda el contador)
│       │       ├── MainActivity.kt       ← Crea el ViewModel y cambia de pantalla
│       │       ├── PantallaExplicacion.kt← Pantalla 1: qué es un ViewModel
│       │       └── PantallaContador.kt   ← Pantalla 2: el contador
│       ├── test/.../ContadorViewModelTest.kt   ← Pruebas del ViewModel (en el PC)
│       └── androidTest/.../PantallasTest.kt    ← Pruebas de las pantallas (emulador)
├── gradle/libs.versions.toml             ← Versiones de las librerías
├── build.gradle.kts / settings.gradle.kts← Configuración general de Gradle
└── README.md
```

---

## Funcionamiento paso a paso

1. **Abres la app.** Android crea `MainActivity` y ejecuta `onCreate`.
2. **`MainActivity` pide el ViewModel** con `viewModel()`. Como es la primera vez, Android crea un `ContadorViewModel` nuevo, con el contador en `0`.
3. **Se muestra la pantalla 1**, porque la variable `mostrarContador` empieza en `false`.
4. **Pulsas «Probar ejemplo».** Se ejecuta `mostrarContador.value = true` y Compose dibuja la pantalla 2.
5. **La pantalla 2 observa el contador** con `contadorViewModel.contador.collectAsState()` y muestra `0`.
6. **Pulsas «+1».** El botón llama a `contadorViewModel.sumarUno()`. El ViewModel hace `0 + 1` y guarda `1`.
7. **La pantalla se actualiza sola** y muestra `1`, porque está observando el `StateFlow`.
8. **Giras el teléfono.** Android destruye y vuelve a crear `MainActivity`. `viewModel()` devuelve **el mismo** `ContadorViewModel`, que sigue teniendo `1`. Además, `rememberSaveable` recuerda que estabas en la pantalla 2.
9. **Pulsas «Volver».** Se ejecuta `mostrarContador.value = false` y aparece la pantalla 1. El ViewModel sigue vivo.
10. **Vuelves a entrar.** El contador sigue en `1`.

Ejemplo: pulsas «+1» tres veces → `3`. Giras el teléfono → sigue `3`. Pulsas «Reiniciar» → `0`.

---

## Cómo explicar este código al profesor

**1. ¿Qué es Kotlin y por qué se utilizó?**
Kotlin es el lenguaje de programación recomendado por Google para crear apps Android. Es moderno, más corto que Java y lo pidió el enunciado. Toda la app (pantallas, ViewModel y pruebas) está en Kotlin.

**2. ¿Qué es una pantalla o View?**
Es lo que el usuario ve y toca: textos y botones. En esta app cada pantalla es una función de Kotlin marcada con `@Composable` (Jetpack Compose). Hay dos: `PantallaExplicacion` y `PantallaContador`.

**3. ¿Qué es un ViewModel?**
Es una clase que guarda los datos de la pantalla y la lógica para cambiarlos. Aquí es `ContadorViewModel`: guarda el número y tiene dos funciones, `sumarUno()` y `reiniciar()`.

**4. ¿Por qué la app solo tiene un ViewModel?**
Porque solo hay un dato que cambia: el contador. La pantalla 1 solo muestra texto, no necesita ViewModel. Además, el enunciado pedía uno solo.

**5. ¿Qué ocurre cuando pulso el botón +1?**
El botón ejecuta `contadorViewModel.sumarUno()`. Esa función lee el valor actual (`_contador.value`), le suma 1 y guarda el resultado. La pantalla **no** suma nada: solo avisa al ViewModel.

**6. ¿Cómo llega el nuevo número a la pantalla?**
El contador está guardado en un `StateFlow`, que es como una caja que avisa cuando su contenido cambia. La pantalla la observa con `collectAsState()`. Cuando el ViewModel cambia el valor, Compose vuelve a dibujar el texto con el número nuevo, automáticamente.

**7. ¿Por qué el contador conserva su valor al recrearse la actividad?**
Al girar el teléfono, Android destruye y vuelve a crear la Activity, pero **no** el ViewModel: lo guarda aparte mientras la Activity siga en uso. Cuando la nueva Activity llama a `viewModel()`, recibe el mismo objeto con el mismo número. (Si Android cierra la app por completo, el número sí se pierde, porque el ViewModel vive en memoria.)

**8. ¿Qué archivos debo abrir primero si el profesor quiere revisar el código?**
En este orden, dentro de `app/src/main/java/com/luiscruz/viewmodelfacil/`:
1. `ContadorViewModel.kt` — el ViewModel (lo más importante).
2. `PantallaContador.kt` — cómo la pantalla usa el ViewModel.
3. `MainActivity.kt` — dónde se crea el ViewModel y cómo se cambia de pantalla.
4. `PantallaExplicacion.kt` — la pantalla de teoría.
5. `app/src/test/.../ContadorViewModelTest.kt` — las pruebas del contador.

---

## Pruebas automatizadas

- **`ContadorViewModelTest`** (pruebas unitarias, en el PC): el contador empieza en 0, `sumarUno()` suma 1, sumar tres veces da 3 y `reiniciar()` vuelve a 0.
- **`PantallasTest`** (pruebas de interfaz, en emulador): navega entre las dos pantallas, pulsa los botones y comprueba que el contador se conserva al volver y al recrear la Activity (como al girar el teléfono).
