package com.luiscruz.viewmodelfacil

// Color transparente de Android (para las barras de arriba y abajo del teléfono).
import android.graphics.Color.TRANSPARENT
// Bundle: datos que Android entrega al crear la Activity.
import android.os.Bundle
// ComponentActivity: tipo de Activity preparado para usar Compose y ViewModels.
import androidx.activity.ComponentActivity
// SystemBarStyle y enableEdgeToEdge: controlan cómo se ven las barras del sistema.
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
// BackHandler: decide qué pasa al pulsar el botón "Atrás" del teléfono.
import androidx.activity.compose.BackHandler
// setContent: indica qué pantallas de Compose se dibujan en la Activity.
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
// mutableStateOf + rememberSaveable: guardan qué pantalla se está mostrando.
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
// viewModel(): función que nos entrega el ViewModel (lo crea solo la primera vez).
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * MainActivity es la única Activity de la aplicación.
 *
 * Una Activity es la "ventana" que Android abre cuando tocas el icono de la app.
 * Dentro de ella mostramos UNA de nuestras dos pantallas:
 *  - PantallaExplicacion (pantalla 1)
 *  - PantallaContador    (pantalla 2)
 */
class MainActivity : ComponentActivity() {

    /**
     * onCreate se ejecuta cuando Android crea la Activity.
     *
     * OJO: también se ejecuta otra vez cada vez que giras el teléfono,
     * porque Android destruye la Activity y la vuelve a crear.
     * Aun así, el ViewModel NO se vuelve a crear: Android nos devuelve el mismo.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // La app se dibuja de borde a borde de la pantalla.
        // "light" pide iconos oscuros en las barras del sistema, porque nuestro fondo es claro.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(TRANSPARENT, TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(TRANSPARENT, TRANSPARENT)
        )

        // Todo lo que está dentro de setContent { } es la interfaz de la app.
        setContent {

            // Colores de la app: azul y blanco con un fondo claro.
            val colores = lightColorScheme(
                primary = Color(0xFF1565C0),    // Azul de títulos y botones.
                onPrimary = Color.White,        // Color del texto encima del azul.
                background = Color(0xFFF3F7FC), // Fondo general (casi blanco, un poco azulado).
                surface = Color.White,          // Fondo de las tarjetas.
                onSurface = Color(0xFF1B1F24)   // Color del texto normal (casi negro).
            )

            // MaterialTheme aplica esos colores a todos los botones y textos.
            MaterialTheme(colorScheme = colores) {

                // Surface es el "papel" de fondo que ocupa toda la pantalla.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // ---------------- EL ÚNICO VIEWMODEL ----------------
                    // Pedimos el ContadorViewModel a Android.
                    // - La primera vez, Android lo crea.
                    // - Las siguientes veces (al girar el teléfono o al volver a la
                    //   pantalla del contador), Android devuelve EL MISMO objeto.
                    // Por eso el número del contador no se pierde.
                    val contadorViewModel: ContadorViewModel = viewModel()

                    // ---------------- NAVEGACIÓN ----------------
                    // Variable que indica qué pantalla se ve:
                    //   false -> pantalla 1 (explicación)
                    //   true  -> pantalla 2 (contador)
                    // "rememberSaveable" la recuerda incluso al girar el teléfono,
                    // así seguimos en la misma pantalla después de girarlo.
                    val mostrarContador = rememberSaveable { mutableStateOf(false) }

                    if (mostrarContador.value) {
                        // Si el usuario pulsa "Atrás" en el teléfono,
                        // volvemos a la pantalla 1 en lugar de cerrar la app.
                        BackHandler {
                            mostrarContador.value = false
                        }

                        // Pantalla 2. Le pasamos el ViewModel y lo que debe hacer
                        // el botón "Volver" (cambiar a la pantalla 1).
                        PantallaContador(
                            contadorViewModel = contadorViewModel,
                            alVolver = { mostrarContador.value = false }
                        )
                    } else {
                        // Pantalla 1. Le pasamos lo que debe hacer el botón
                        // "Probar ejemplo" (cambiar a la pantalla 2).
                        PantallaExplicacion(
                            alProbarEjemplo = { mostrarContador.value = true }
                        )
                    }
                }
            }
        }
    }
}
