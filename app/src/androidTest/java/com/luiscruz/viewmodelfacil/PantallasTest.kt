package com.luiscruz.viewmodelfacil

// Herramientas para probar pantallas de Compose en el emulador.
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Rule
import org.junit.Test

/**
 * Pruebas de interfaz: se ejecutan en el emulador (o en un teléfono)
 * y pulsan los botones como lo haría una persona.
 */
class PantallasTest {

    // Abre MainActivity antes de cada prueba.
    @get:Rule
    val regla = createAndroidComposeRule<MainActivity>()

    // Navegar, sumar, reiniciar, volver y comprobar que el valor se conserva.
    @Test
    fun navegacionYContadorFuncionan() {
        // Pantalla 1 visible.
        regla.onNodeWithText("¿Qué es un ViewModel?").assertIsDisplayed()

        // Ir a la pantalla 2.
        regla.onNodeWithText("Probar ejemplo").performScrollTo().performClick()
        regla.onNodeWithText("Ejemplo práctico").assertIsDisplayed()
        regla.onNodeWithText("0").assertIsDisplayed()

        // Pulsar +1 tres veces -> 3.
        regla.onNodeWithText("+1").performClick()
        regla.onNodeWithText("+1").performClick()
        regla.onNodeWithText("+1").performClick()
        regla.onNodeWithText("3").assertIsDisplayed()

        // Reiniciar -> 0.
        regla.onNodeWithText("Reiniciar").performClick()
        regla.onNodeWithText("0").assertIsDisplayed()

        // Sumar 1 y volver a la pantalla 1.
        regla.onNodeWithText("+1").performClick()
        regla.onNodeWithText("Volver").performScrollTo().performClick()
        regla.onNodeWithText("¿Qué es un ViewModel?").assertIsDisplayed()

        // Entrar otra vez: el ViewModel sigue vivo, el valor sigue en 1.
        regla.onNodeWithText("Probar ejemplo").performScrollTo().performClick()
        regla.onNodeWithText("1").assertIsDisplayed()
    }

    // Recrear la Activity (como al girar el teléfono) no borra el contador.
    @Test
    fun elContadorSobreviveALaRecreacionDeLaActividad() {
        regla.onNodeWithText("Probar ejemplo").performScrollTo().performClick()
        regla.onNodeWithText("+1").performClick()
        regla.onNodeWithText("+1").performClick()
        regla.onNodeWithText("2").assertIsDisplayed()

        // Android destruye y vuelve a crear la Activity.
        regla.activityRule.scenario.recreate()

        // Seguimos en la pantalla 2 y el número sigue siendo 2.
        regla.onNodeWithText("Ejemplo práctico").assertIsDisplayed()
        regla.onNodeWithText("2").assertIsDisplayed()
    }
}
