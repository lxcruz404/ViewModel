package com.luiscruz.viewmodelfacil

// Herramientas de Compose para organizar elementos en la pantalla.
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
// safeDrawingPadding: deja espacio para la barra de estado y la de navegación.
import androidx.compose.foundation.layout.safeDrawingPadding
// rememberScrollState + verticalScroll: permiten desplazar la pantalla si no cabe
// (por ejemplo, con el teléfono en horizontal).
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
// Componentes visuales de Material 3.
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
// collectAsState: convierte el StateFlow del ViewModel en un valor que Compose observa.
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * PANTALLA 2: ejemplo práctico de un contador con ViewModel.
 *
 * La pantalla NO guarda el número. Solo:
 *  - Lee el número desde el ViewModel y lo muestra.
 *  - Le pide al ViewModel que cambie el número cuando pulsas un botón.
 *
 * @param contadorViewModel el único ViewModel de la app (lo crea MainActivity).
 * @param alVolver código que se ejecuta al pulsar "Volver" (regresa a la pantalla 1).
 */
@Composable
fun PantallaContador(
    contadorViewModel: ContadorViewModel,
    alVolver: () -> Unit
) {
    // Observamos el contador del ViewModel.
    // Cada vez que el ViewModel cambia el número, esta pantalla se vuelve
    // a dibujar sola con el valor nuevo. "estadoContador.value" es el número actual.
    val estadoContador = contadorViewModel.contador.collectAsState()

    // Column coloca los elementos uno debajo de otro, centrados.
    Column(
        modifier = Modifier
            .fillMaxSize()                          // Ocupa toda la pantalla.
            .safeDrawingPadding()                   // No se mete debajo de las barras del sistema.
            .verticalScroll(rememberScrollState())  // Se puede desplazar si no cabe.
            .padding(24.dp),                        // Margen alrededor.
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // Título de la pantalla.
        Text(
            text = "Ejemplo práctico",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )

        // Tarjeta blanca que muestra el número del contador.
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Valor del contador",
                    style = MaterialTheme.typography.titleMedium
                )
                // El número que viene del ViewModel (convertido a texto).
                Text(
                    text = estadoContador.value.toString(),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Row coloca los dos botones uno al lado del otro.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Botón "+1": le pide al ViewModel que sume 1.
            // La pantalla NO suma nada: solo avisa al ViewModel.
            Button(
                onClick = { contadorViewModel.sumarUno() },
                modifier = Modifier.weight(1f) // Ocupa la mitad del ancho.
            ) {
                Text(text = "+1")
            }

            // Botón "Reiniciar": le pide al ViewModel que vuelva a 0.
            OutlinedButton(
                onClick = { contadorViewModel.reiniciar() },
                modifier = Modifier.weight(1f) // Ocupa la otra mitad.
            ) {
                Text(text = "Reiniciar")
            }
        }

        // Explicación breve de lo que está pasando.
        Text(
            text = "Este número lo guarda el ContadorViewModel, no la pantalla. " +
                "Los botones solo le piden al ViewModel que cambie el valor y la " +
                "pantalla se actualiza sola. Gira el teléfono o vuelve atrás y " +
                "entra de nuevo: el número se conserva.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        // Botón "Volver": regresa a la pantalla 1.
        // El ViewModel sigue vivo, así que el número se conserva al volver a entrar.
        TextButton(onClick = alVolver) {
            Text(text = "Volver")
        }
    }
}
