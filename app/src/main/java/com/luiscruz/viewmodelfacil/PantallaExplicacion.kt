package com.luiscruz.viewmodelfacil

// Herramientas de Compose para organizar elementos en la pantalla.
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
// safeDrawingPadding: deja espacio para la barra de estado y la de navegación.
import androidx.compose.foundation.layout.safeDrawingPadding
// rememberScrollState + verticalScroll: permiten desplazar el texto si no cabe.
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
// Componentes visuales de Material 3.
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
// @Composable: marca una función que dibuja interfaz.
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
// "dp" es la unidad de medida de tamaños en Android.
import androidx.compose.ui.unit.dp

/**
 * PANTALLA 1: explica qué es un ViewModel.
 *
 * Esta pantalla solo muestra texto y un botón. No guarda datos,
 * por eso no necesita ningún ViewModel.
 *
 * @param alProbarEjemplo código que se ejecuta al pulsar "Probar ejemplo".
 *        MainActivity lo usa para cambiar a la pantalla del contador.
 */
@Composable
fun PantallaExplicacion(alProbarEjemplo: () -> Unit) {

    // Column coloca los elementos uno debajo de otro.
    Column(
        modifier = Modifier
            .fillMaxSize()                          // Ocupa toda la pantalla.
            .safeDrawingPadding()                   // No se mete debajo de las barras del sistema.
            .verticalScroll(rememberScrollState())  // Se puede desplazar si el texto no cabe.
            .padding(24.dp),                        // Margen alrededor.
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre elementos.
    ) {

        // Título de la pantalla.
        Text(
            text = "¿Qué es un ViewModel?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Tarjeta blanca con la explicación.
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Definición sencilla.
                Text(
                    text = "Un ViewModel es una clase de Android que guarda los datos " +
                        "que muestra una pantalla y la lógica para cambiarlos.",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Idea 1: separar datos e interfaz.
                Text(
                    text = "1. Separa los datos de la interfaz",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "La interfaz (lo que ves) solo dibuja y avisa cuando pulsas " +
                        "un botón. El ViewModel guarda los datos y decide cómo cambian. " +
                        "Así cada parte tiene una sola tarea.",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Idea 2: conservar el estado al girar la pantalla.
                Text(
                    text = "2. Conserva el estado al girar la pantalla",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Cuando giras el teléfono, Android destruye la pantalla y la " +
                        "vuelve a crear. El ViewModel no se destruye en ese momento, " +
                        "así que los datos siguen ahí.",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Aclaración: el ViewModel no es un almacenamiento permanente.
                Text(
                    text = "Ojo: el ViewModel guarda los datos en memoria. Si Android " +
                        "cierra la app por completo, esos datos se pierden.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Botón que lleva a la pantalla 2.
        // Al pulsarlo se ejecuta "alProbarEjemplo", que cambia de pantalla.
        Button(
            onClick = alProbarEjemplo,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Probar ejemplo")
        }
    }
}
