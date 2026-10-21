# ViewModel

Aplicación Android escrita en **Kotlin** que explica, de forma sencilla, el concepto de **ViewModel**.
El nombre visible de la app en el teléfono es **ViewModel**.

Repositorio: https://github.com/lxcruz404/ViewModel

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

## Estructura de archivos importantes

```
ViewModel/
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


## Cómo esta hecho este codigo

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
