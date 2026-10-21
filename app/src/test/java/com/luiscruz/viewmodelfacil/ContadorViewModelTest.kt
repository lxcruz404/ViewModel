package com.luiscruz.viewmodelfacil

// assertEquals compara el valor esperado con el valor real.
import org.junit.Assert.assertEquals
// @Test marca cada función que es una prueba.
import org.junit.Test

/**
 * Pruebas unitarias del ContadorViewModel.
 *
 * Se ejecutan en el PC (sin emulador) y comprueban que la lógica
 * del contador funciona: empezar en 0, sumar 1 y reiniciar.
 */
class ContadorViewModelTest {

    // El contador debe empezar en 0.
    @Test
    fun elContadorEmpiezaEnCero() {
        val viewModel = ContadorViewModel()

        assertEquals(0, viewModel.contador.value)
    }

    // Al llamar a sumarUno() una vez, el contador debe valer 1.
    @Test
    fun sumarUnoIncrementaElContador() {
        val viewModel = ContadorViewModel()

        viewModel.sumarUno()

        assertEquals(1, viewModel.contador.value)
    }

    // Al llamar a sumarUno() tres veces, el contador debe valer 3.
    @Test
    fun sumarUnoVariasVecesAcumulaElValor() {
        val viewModel = ContadorViewModel()

        viewModel.sumarUno()
        viewModel.sumarUno()
        viewModel.sumarUno()

        assertEquals(3, viewModel.contador.value)
    }

    // Después de sumar, reiniciar() debe devolver el contador a 0.
    @Test
    fun reiniciarDevuelveElContadorACero() {
        val viewModel = ContadorViewModel()
        viewModel.sumarUno()
        viewModel.sumarUno()

        viewModel.reiniciar()

        assertEquals(0, viewModel.contador.value)
    }
}
