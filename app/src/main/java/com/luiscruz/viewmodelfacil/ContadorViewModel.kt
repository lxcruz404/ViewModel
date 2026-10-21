package com.luiscruz.viewmodelfacil

// ViewModel: clase base de Android para guardar los datos de una pantalla.
import androidx.lifecycle.ViewModel
// MutableStateFlow: una "caja" que guarda un valor que SÍ se puede cambiar
// y que avisa automáticamente a quien la observe cuando el valor cambia.
import kotlinx.coroutines.flow.MutableStateFlow
// StateFlow: la misma "caja", pero de solo lectura (se puede mirar, no cambiar).
import kotlinx.coroutines.flow.StateFlow

/**
 * ContadorViewModel es el ÚNICO ViewModel de la aplicación.
 *
 * Su trabajo es:
 *  1. Guardar el valor del contador.
 *  2. Sumar 1 al contador.
 *  3. Reiniciar el contador a 0.
 *  4. Avisar a la pantalla cada vez que el valor cambia.
 *
 * Al heredar de ViewModel (": ViewModel()"), Android lo mantiene vivo aunque
 * la pantalla se destruya y se vuelva a crear (por ejemplo, al girar el teléfono).
 * Por eso el número no se pierde.
 *
 * Importante: el ViewModel guarda los datos en memoria. Si Android cierra
 * la app por completo, el contador vuelve a empezar en 0.
 */
class ContadorViewModel : ViewModel() {

    // Valor PRIVADO del contador. Empieza en 0.
    // Es "private" para que SOLO este ViewModel pueda cambiarlo.
    // (El guion bajo "_" es una costumbre de Kotlin para marcar la versión privada).
    private val _contador = MutableStateFlow(0)

    // Valor PÚBLICO del contador, de solo lectura.
    // La pantalla lo observa: cada vez que cambia, la pantalla se vuelve a dibujar sola.
    val contador: StateFlow<Int> = _contador

    /**
     * Suma 1 al contador.
     * La pantalla llama a esta función cuando se pulsa el botón "+1".
     */
    fun sumarUno() {
        // 1. Leemos el valor actual del contador.
        val valorActual = _contador.value
        // 2. Guardamos el valor actual + 1.
        //    Al cambiar ".value", la pantalla recibe el aviso y muestra el nuevo número.
        _contador.value = valorActual + 1
    }

    /**
     * Pone el contador otra vez en 0.
     * La pantalla llama a esta función cuando se pulsa el botón "Reiniciar".
     */
    fun reiniciar() {
        _contador.value = 0
    }
}
