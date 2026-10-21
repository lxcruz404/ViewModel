// build.gradle.kts del módulo "app".
// Explica a Gradle cómo compilar la aplicación Android y qué librerías necesita.

plugins {
    // Plugin para crear una aplicación Android (ya incluye soporte para Kotlin).
    alias(libs.plugins.android.application)
    // Plugin necesario para usar Jetpack Compose
    // (la forma moderna de dibujar pantallas escribiendo solo código Kotlin).
    alias(libs.plugins.kotlin.compose)
}

android {
    // Paquete donde está el código Kotlin de la app.
    namespace = "com.luiscruz.viewmodelfacil"

    // Versión de Android con la que se compila la app.
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        // Identificador único de la app en el teléfono.
        applicationId = "com.luiscruz.viewmodelfacil"
        // Versión mínima de Android que puede instalar la app (Android 8.0).
        minSdk = 26
        // Versión de Android para la que está preparada la app.
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        // Programa que ejecuta las pruebas en el emulador o en un teléfono.
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Versión de Java con la que se genera el código final.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        // Activa Jetpack Compose en este módulo.
        compose = true
    }
}

dependencies {
    // Compose: lista de versiones compatibles entre sí (BOM).
    implementation(platform(libs.androidx.compose.bom))
    // Compose: piezas básicas de la interfaz (textos, columnas, etc.).
    implementation(libs.androidx.compose.ui)
    // Compose: componentes con diseño Material 3 (botones, tarjetas, colores).
    implementation(libs.androidx.compose.material3)
    // Permite mostrar pantallas de Compose dentro de una Activity.
    implementation(libs.androidx.activity.compose)
    // Incluye la clase ViewModel y la función viewModel() para usarla en Compose.
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Pruebas unitarias (se ejecutan en el PC, sin emulador).
    testImplementation(libs.junit)

    // Pruebas de interfaz (se ejecutan en el emulador o en un teléfono).
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Necesario para que las pruebas de Compose puedan abrir pantallas.
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
